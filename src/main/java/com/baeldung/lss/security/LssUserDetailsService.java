 package com.baeldung.lss.security;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.baeldung.lss.model.Roles;
import com.baeldung.lss.model.User;
import com.baeldung.lss.persistence.UserRepository;

@Service
@Transactional
public class LssUserDetailsService implements UserDetailsService {
	

    private static final String ROLE_USER = "ROLE_USER";

    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final User user = userRepository.findByEmail(email);
        
        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + email);
        } 
       

        //return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getEnabled(), true, true, true, getAuthorities(ROLE_USER));
        return new MyUserDetails(user);
    }

   
    

   
    
 
}