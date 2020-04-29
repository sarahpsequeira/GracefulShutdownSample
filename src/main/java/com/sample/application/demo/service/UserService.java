package com.sample.application.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.sample.application.demo.dto.UserGetResponse;
import com.sample.application.demo.event.UserAccessedEvent;
import com.sample.application.demo.model.User;
import com.sample.application.demo.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;


    Logger logger = LoggerFactory.getLogger(UserService.class);

    public List<UserGetResponse> getUsersByLastName(String lastName){
        List<User>users=userRepository.findByLastName(lastName);
        logger.debug("Success found users by last name");
        List<UserGetResponse>userGetResponseList=new ArrayList<>();
        for(User user:users){
            userGetResponseList.add(getUserGetResponse(user));
        }
        UserAccessedEvent userAccessedEvent = new UserAccessedEvent(this,"Test users have been accessed");
        applicationEventPublisher.publishEvent(userAccessedEvent);
        return userGetResponseList;
    }

    public UserGetResponse getUserGetResponse(User user){
        UserGetResponse userGetResponse=new UserGetResponse(user.getFirstName(),user.getLastName(),user.getAddress());
        return userGetResponse;

    }
}
