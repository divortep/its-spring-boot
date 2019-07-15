package com.itsspringboot.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.itsspringboot.service.FCMNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class TestFCMController {

  private FCMNotificationService fcmNotificationService;

  @Autowired
  public TestFCMController(FCMNotificationService fcmNotificationService) {
    this.fcmNotificationService = fcmNotificationService;
  }

  @RequestMapping("/testMessage")
  @ResponseStatus(value = HttpStatus.OK)
  public void testFCMMessaging() throws FirebaseMessagingException {
    fcmNotificationService.notify("Test message", "Test message text second line");
  }
}
