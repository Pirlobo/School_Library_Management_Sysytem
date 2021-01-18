package com.baeldung.lss.config;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.baeldung.lss.model.Roles;
import com.baeldung.lss.model.User;
import com.baeldung.lss.service.UserService;

@Configuration
public class CustomLogInSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired
	private UserService userService; 
	
	@Override
	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		String defaultURL = determineUrlBasedOnRoles();
		RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

		if (response.isCommitted()) {
			return;
		}

		redirectStrategy.sendRedirect(request, response, defaultURL);

	}
	// Use this method when you use the default role authentication flow in Spring Security, but the role authentication in this 
	// project is a custom. 
	
//	@Override
//	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
//		
//		String defaultURLForUserRole = "/loginSuccessful";
//		String defaultURLForAdminRole = "/admin";
//		// TODO Auto-generated method stub
//		if (request.isUserInRole("User")) {
//			return defaultURLForUserRole;
//		} else if (request.isUserInRole("Admin")) {
//			return defaultURLForAdminRole;
//		}
//		
//		return null;
//	}
	
	public String determineUrlBasedOnRoles() {
		String defaultURLForUserRole = "/loginSuccessful";
		String defaultURLForAdminRole = "/admin";
		
		User user = userService.getCurrentLoggedUser();
		Set<Roles> roles = user.getRoles();
		for (Roles role : roles) {
			if (role.getName().equals("User")) {
				return defaultURLForUserRole;
			} else if (role.getName().equals("Admin")){
				return defaultURLForAdminRole;
			}
		}
		return null;
		
	}
}
