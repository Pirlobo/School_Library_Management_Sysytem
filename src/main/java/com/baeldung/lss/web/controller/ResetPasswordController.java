 package com.baeldung.lss.web.controller;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.baeldung.lss.model.AccountStatus;
import com.baeldung.lss.model.PasswordResetToken;
import com.baeldung.lss.model.UpdatedStudent;
import com.baeldung.lss.model.User;
import com.baeldung.lss.model.VerificationToken;
import com.baeldung.lss.persistence.UserRepository;
import com.baeldung.lss.registration.OnRegistrationEvent;
import com.baeldung.lss.registration.OnResetPasswordEvent;
import com.baeldung.lss.service.UserService;
import com.baeldung.lss.validation.EmailExistsException;
import com.baeldung.lss.validation.PasswordMatcherException;



@Controller
public class ResetPasswordController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	 @Autowired
	    private ApplicationEventPublisher eventPublisher;
	
	@GetMapping("/forgetPassword")
	public ModelAndView forgotPassword(Model model, @ModelAttribute("updatedUser") UpdatedStudent updatedUser ) {
		 return new ModelAndView("resetPassword", "updatedUser", updatedUser);
	}
	

	
	
	@PostMapping("/changePassword")
	 public ModelAndView changePassword(@Valid @ModelAttribute("updatedUser") UpdatedStudent updatedUser, final HttpServletRequest request, final RedirectAttributes redirectAttributes , final BindingResult result ) throws EmailExistsException, PasswordMatcherException {
		try {
			
			User user = userService.updateExistingUser(updatedUser);
			
			final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	         eventPublisher.publishEvent(new OnResetPasswordEvent(user, appUrl));
		} catch (EmailExistsException e) {
			result.addError(new FieldError("updatedUser", "email", e.getMessage()));
			
			return new ModelAndView("resetPassword", "updatedUser", updatedUser);
		}
		catch (PasswordMatcherException e) {
			result.addError(new FieldError("updatedUser", "passwordConfirmation", e.getMessage()));
			return new ModelAndView("resetPassword", "updatedUser", updatedUser);
		}
		redirectAttributes.addFlashAttribute("message", "Your confirmation has been sent via ur email");
		return new ModelAndView("redirect:/login");
       
	}
	
	@RequestMapping(value = "/resetPasswordConfirm")
    public ModelAndView confirmResetPassword(@RequestParam("token") final String token, final RedirectAttributes redirectAttributes) {
        final PasswordResetToken passwordResetToken = userService.getPasswordResetToken(token);
        if (passwordResetToken == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid account confirmation token.");
            return new ModelAndView("redirect:/login");
        }

        final User user = passwordResetToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((passwordResetToken.getExpiryDate()
            .getTime()
            - cal.getTime()
                .getTime()) <= 0) {
            redirectAttributes.addFlashAttribute("errorMessage", "Your registration token has expired. Please register again.");
            return new ModelAndView("redirect:/login");
        }
        if (user == null) {
			System.out.println("null");
		
			 return new ModelAndView("redirect:/signup");
        }
        
        
        String newPassword = user.getNewPassword();
        user.setPassword(newPassword);
        user.setAccountStatus(AccountStatus.Active);
        
        userRepository.save(user);
        redirectAttributes.addFlashAttribute("message", "Reset Password successfully");
        return new ModelAndView("redirect:/login");
    }
	
	
	
}
