package com.sample.application.demo.event;

import org.springframework.context.ApplicationEvent;

public class UserAccessedEvent extends ApplicationEvent {
    private String message;

    public UserAccessedEvent(Object source, String message) {
        super(source);
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
