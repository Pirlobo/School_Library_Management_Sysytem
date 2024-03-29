package com.baeldung.lss.web.controller;




import java.net.http.HttpRequest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.mail.Message;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.integration.support.MessageBuilder;
//import org.springframework.messaging.MessageChannel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.baeldung.lss.model.AccountStatus;
import com.baeldung.lss.model.User;
import com.baeldung.lss.model.VerificationToken;
import com.baeldung.lss.registration.OnRegistrationEvent;
import com.baeldung.lss.service.IUserService;
import com.baeldung.lss.validation.EmailExistsException;




@Controller
class RegistrationController {

    @Autowired
    private ApplicationEventPublisher eventPublisher;
  
    @Autowired
    private IUserService userService; 

    @Autowired
    @Qualifier("registrationRequest")
    private MessageChannel registrationRequestChannel;
    
   

    @GetMapping(value = "/signup")
    public ModelAndView registrationForm() {
        return new ModelAndView("registrationPage", "user", new User());
    }

    @PostMapping(value = "/signup")
    public ModelAndView registerUser(@Valid final User user, final BindingResult result, final HttpServletRequest request, final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return new ModelAndView("registrationPage", "user", user);
        }
        try {
        	
            final User registered = userService.registerNewUser(user);
            
   

          final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
         eventPublisher.publishEvent(new OnRegistrationEvent(registered, appUrl));
         // muticast(Application event)
        } catch (EmailExistsException e) {
            result.addError(new FieldError("user", "email", e.getMessage()));
            return new ModelAndView("registrationPage", "user", user);
        }
        redirectAttributes.addFlashAttribute("message", "Your confirmation has been sent via ur email");
        return new ModelAndView("redirect:/login");
        
    }
    
    
    @RequestMapping(value = "/registrationConfirm")
    public ModelAndView confirmRegistration(@RequestParam("token") final String token, final RedirectAttributes redirectAttributes) {
        final VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid account confirmation token.");
            return new ModelAndView("redirect:/login");
        }

        final User user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate()
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
        
        user.setEnabled(true);
        user.setAccountStatus(AccountStatus.Active);
        userService.saveRegisteredUser(user);
        redirectAttributes.addFlashAttribute("message", "Your account verified successfully");
        return new ModelAndView("redirect:/login");
    }


    @RequestMapping("/integration")
    @ResponseBody
    public String test(@RequestParam("test") String test) {
    	org.springframework.messaging.Message<String> message = MessageBuilder.withPayload(test).build();
    	registrationRequestChannel.send(message); 
		return "Ok";
	}
   
    



}
