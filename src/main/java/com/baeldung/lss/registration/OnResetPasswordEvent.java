package com.baeldung.lss.registration;



import com.baeldung.lss.model.User;
import org.springframework.context.ApplicationEvent;

public class OnResetPasswordEvent extends ApplicationEvent {

    private final String appUrl;
    private final User user;

    public OnResetPasswordEvent(final User user, final String appUrl) {
        super(user);
        this.user = user;
        this.appUrl = appUrl;
    }

    //

    public String getAppUrl() {
        return appUrl;
    }

    public User getUser() {
        return user;
    }

}
