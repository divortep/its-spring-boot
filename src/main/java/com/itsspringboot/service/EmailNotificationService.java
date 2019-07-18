package com.itsspringboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService {

  @Value("${its.admin.email}")
  private String adminEmail;

  @Value("${its.tasker.email}")
  private String taskerEmail;

  @Value("${its.ikea.email}")
  private String ikeaEmail;

  private JavaMailSender mailSender;

  @Autowired
  public EmailNotificationService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void notifyAdmin(String subject, String message) {
    sendMessage(adminEmail, subject, message);
  }

  public void notifyTasker(String subject, String message) {
    sendMessage(taskerEmail, subject, message);
  }

  public void notifyIKEA(String subject, String message) {
    sendMessage(ikeaEmail, subject, message);
  }

  private void sendMessage(String to, String subject, String message) {
    SimpleMailMessage msg = new SimpleMailMessage();
    msg.setTo(to);
    msg.setSubject(subject);
    msg.setText(message);
    mailSender.send(msg);
  }
}
