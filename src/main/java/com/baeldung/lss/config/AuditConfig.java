package com.baeldung.lss.config;

import java.time.ZonedDateTime;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.baeldung.lss.model.SpringSecurityAuditorAware;
import com.baeldung.lss.model.User;

/**
 * @author Rashidi Zin
 */
@Configuration
//@EnableJpaAuditing(auditorAwareRef = "auditorProvider", dateTimeProviderRef = "auditingDateTimeProvider")

public class AuditConfig {

    @Bean
    public AuditorAware<User> auditorProvider() { 
        return new SpringSecurityAuditorAware();
    }
    
    @Bean // Makes ZonedDateTime compatible with auditing fields
    public DateTimeProvider auditingDateTimeProvider() {
        return () -> Optional.of(ZonedDateTime.now());
    }

}
