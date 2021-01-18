package com.baeldung.lss.web.controller;



import java.security.Principal;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.metadata.ValidateUnwrappedValue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.baeldung.lss.model.User;
import com.baeldung.lss.persistence.UserRepository;

import io.jsonwebtoken.io.IOException;
import lombok.val;
//@CrossOrigin(origins = "http://localhost:3001")
@Controller
public class LoginController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping(value = "/test")
	@Cacheable(value = "userCache")
	@ResponseBody
	public User findByEmail(@RequestParam("email") String email) {
			User user  = userRepository.findByEmail(email);
			return user;

	}
	
	
	@GetMapping(value = "/loginSuccessful")
	public String successfullLogInRedirect() {
		
			return "successfullPage.html";

	}
	
	@GetMapping(value = "/login")
	public String login() {
		
		return "loginPage.html";

	}
	
	@GetMapping(value = "/loginFailed")
	public String failedLogInRedirect(final RedirectAttributes redirectAttributes) {
		
		redirectAttributes.addFlashAttribute("msg", "Bad Credentials And Principals");
		
		//return new ModelAndView("redirect:/login");
		return "redirect:/login";

	}
	

	@GetMapping(value = "/admin")
	@ResponseBody
	public void admin() {
		System.out.println("ADMIN");
	}
	
	

	

	
}
