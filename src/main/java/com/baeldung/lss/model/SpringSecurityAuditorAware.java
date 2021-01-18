package com.baeldung.lss.model;

import java.util.Optional;

import org.bouncycastle.jcajce.provider.asymmetric.dsa.DSASigner.stdDSA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.baeldung.lss.persistence.UserRepository;
import com.baeldung.lss.security.MyUserDetails;

public class SpringSecurityAuditorAware implements AuditorAware<User>{
	
	@Autowired
	UserRepository userRepository;
  
	@Override
	public Optional<User> getCurrentAuditor() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		  if (authentication == null || !authentication.isAuthenticated()) {
		   return null;
		  }
		   
		  return ( (MyUserDetails) authentication.getPrincipal()).getUser();
		 
}
//	@Override
//	public Optional<String> getCurrentAuditor() {
//		
//		return Optional.of("Bo");
//}
	
	
}
  