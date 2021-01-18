package com.baeldung.lss.listener;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.baeldung.lss.model.User;
import com.baeldung.lss.registration.OnRegistrationEvent;
import com.baeldung.lss.registration.OnResetPasswordEvent;
import com.baeldung.lss.service.IUserService;

@Component
public class OnResetPasswordListener implements ApplicationListener<OnResetPasswordEvent> {

    @Autowired
    private IUserService service;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    // API

    @Override
    public void onApplicationEvent(final OnResetPasswordEvent event) {
        this.confirmResetPassword(event);
    }

    private void confirmResetPassword(final OnResetPasswordEvent event) {
        final User user = event.getUser();
        final String token = UUID.randomUUID()
            .toString();
        service.createPasswordResetTokenForUser(user, token);

        final SimpleMailMessage email = constructEmailMessage(event, user, token);
        mailSender.send(email);
    }

    //

    private SimpleMailMessage constructEmailMessage(final OnResetPasswordEvent event, final User user, final String token) {
        final String recipientAddress = user.getEmail();
        final String subject = "Reset Password Confirmation";
        final String confirmationUrl = event.getAppUrl() + "/resetPasswordConfirm?token=" + token;
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText("Please open the following URL to verify your account: \r\n" + confirmationUrl);
        email.setFrom("bonguyens2001@gmail.com");
        return email;
    }

}
