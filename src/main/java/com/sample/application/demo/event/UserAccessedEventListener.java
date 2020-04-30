package com.sample.application.demo.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.amazonaws.services.sns.AmazonSNS;

@Component
public class UserAccessedEventListener implements ApplicationListener<UserAccessedEvent> {

    private final Logger logger = LoggerFactory.getLogger(UserAccessedEventListener.class);

    NotificationMessagingTemplate notificationMessagingTemplate;

    @Value("${cloud.aws.sns.topic.userAccess}")
    String snsTopicName;

    @Autowired
    public UserAccessedEventListener(AmazonSNS amazonSNS) {
        this.notificationMessagingTemplate = new NotificationMessagingTemplate(amazonSNS);
    }

    @Override
    public void onApplicationEvent(UserAccessedEvent userAccessedEvent) {
        logger.info("Received user accessed event - " + userAccessedEvent.getMessage());
        publishMessageToAWS(userAccessedEvent);

    }

    void publishMessageToAWS(UserAccessedEvent userAccessedEvent) {
        this.notificationMessagingTemplate
                .sendNotification(snsTopicName, userAccessedEvent, "Users have been accessed");
        logger.info("Published event to SNS Topic!");
    }

}
