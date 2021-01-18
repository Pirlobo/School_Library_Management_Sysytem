package com.baeldung.lss.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.baeldung.lss.security.LssUserDetailsService;

@EnableWebSecurity
@Configuration
public class LssSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private CustomLogInSuccessHandler successHandler;
    
    @Autowired
    private CustomLoginFailureHandler failureHandler;

    public LssSecurityConfig() {
        super();
    }

    //

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { // @formatter:off
        auth.userDetailsService(userDetailsService);
    } 
    
    
    @Bean
    public UserDetailsService userDetailsService() {
        return new LssUserDetailsService();
    }
     
    
     
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
         
        return authProvider;
    }
 
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { // @formatter:off
        http
        .authorizeRequests()
                .antMatchers("/signup",
                        "/user/register",
                        "/registrationConfirm*",
                        "/badUser*",
                        "/forgetPassword",
                        "/resetPasswordConfirm",
                        "/user/resetPassword*",
                        "/changePassword*",
                        "/user/savePassword*",
                        "/integration",
                        "/js/**", "/loginFailed").permitAll()
                .antMatchers("/admin").hasAuthority("Admin")
                .antMatchers("/user").hasAuthority("User")
                .antMatchers("/loginSuccessful").hasAuthority("User")
                
                .anyRequest().authenticated()

        .and()
        .formLogin()
        .loginPage("/login").permitAll()
        .loginProcessingUrl("/doLogin")
            .successHandler(successHandler)
            .failureHandler(failureHandler)
        .and()
        .logout().permitAll().logoutUrl("/doLogout")
        .logoutSuccessUrl("/login")
        .and()
        .rememberMe()   
        .tokenValiditySeconds(604800)
        .key("lssAppKey")
        //.useSecureCookie(true)
        .rememberMeCookieName("sticky-cookie")
        .rememberMeParameter("remember")        
        
      

        .and()
        .csrf().disable()
        ;
    } // @formatter:on

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
