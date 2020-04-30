package com.sample.application.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.application.demo.dto.UserGetResponse;
import com.sample.application.demo.repository.UserRepository;
import com.sample.application.demo.service.AuditLogService;
import com.sample.application.demo.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuditLogService auditLogService;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/lastName/{lastName}")
    public List<UserGetResponse> findByLastName(@PathVariable String lastName) throws InterruptedException {
        logger.info("Received request to get users by last name, time: {}", LocalDateTime.now());
        List<UserGetResponse> userGetResponses = userService.getUsersByLastName(lastName);
        logger.info("Received response, Going to publish message to MQ, time: {}", LocalDateTime.now());
        auditLogService.publishMessage();
        return userGetResponses;
    }

    @GetMapping
    public Iterable findAll() {
        return userRepository.findAll();
    }

}
