package com.itsspringboot.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FCMNotificationService {

  private final static Logger logger = LoggerFactory.getLogger(FCMNotificationService.class);

  @Value("${its.fcm.notification.topic}")
  private String fcmTopic;

  public void notify(String subject, String message) throws FirebaseMessagingException {

    Message fsmMessage = Message.builder()
        .setTopic(fcmTopic)
        .setNotification(new Notification(subject, message))
        .build();

    FirebaseMessaging.getInstance().send(fsmMessage);
  }
}
