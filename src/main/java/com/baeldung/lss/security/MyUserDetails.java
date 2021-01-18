package com.baeldung.lss.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.baeldung.lss.model.Roles;
import com.baeldung.lss.model.User;
import com.baeldung.lss.persistence.UserRepository;

public class MyUserDetails implements UserDetails{
	@Autowired
	UserRepository userRepository;
	
	private User user;
    
    public MyUserDetails(User user) {
        this.user = user;
    }
    
	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
		
		
		
        Set<Roles> roles = user.getRoles();
        
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        
        for (Roles role : roles) {
        	
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
         
        return authorities;
    }


	@Override
    public String getPassword() {
        return user.getPassword();
    }
 
    @Override
    public String getUsername() {
        return user.getEmail();
    }
 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @Override
    public boolean isEnabled() {
        if (user.getEnabled() == true) {
			return true;
		} else {
			return false;
		}
    }
	public Optional<User> getUser() {
			User user = userRepository.findByEmail("bonguyens2001@gmail.com");
		    Optional<User> opt = Optional.ofNullable(user);
		    return opt;
	
	}
 
}

