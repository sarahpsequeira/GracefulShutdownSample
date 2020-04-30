package com.sample.application.demo.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.sample.application.demo.controller.UserController;
import com.sample.application.demo.event.UserAccessedEvent;

@Service
public class AuditLogService {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Async
    public void publishMessage(){
        UserAccessedEvent userAccessedEvent = new UserAccessedEvent(this,"Test users have been accessed");
        applicationEventPublisher.publishEvent(userAccessedEvent);
        logger.info("Successfully published event, time: {}", LocalDateTime.now());

    }
}
