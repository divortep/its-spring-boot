package com.itsspringboot.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.itsspringboot.service.EmailNotificationService;
import com.itsspringboot.service.FCMNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class TestController {

  private FCMNotificationService fcmNotificationService;
  private EmailNotificationService emailNotificationService;

  @Autowired
  public TestController(final FCMNotificationService fcmNotificationService,
                        final EmailNotificationService emailNotificationService) {
    this.fcmNotificationService = fcmNotificationService;
    this.emailNotificationService = emailNotificationService;
  }

  @RequestMapping("/fcm/testMessage")
  @ResponseStatus(value = HttpStatus.OK)
  public void testFCMMessaging() throws FirebaseMessagingException {
    fcmNotificationService.notify("Test message", "Test message text second line");
  }

  @RequestMapping("/email/testMessage")
  @ResponseStatus(value = HttpStatus.OK)
  public void testEmailMessaging() {
    emailNotificationService.notifyAdmin("Test", "test");
  }
}
