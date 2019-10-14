package com.itsspringboot.service;

import java.util.function.Supplier;
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

  private Supplier<JavaMailSender> javaMailSenderFactory;

  @Autowired
  public void setJavaMailSenderFactory(
      final Supplier<JavaMailSender> javaMailSenderFactory) {
    this.javaMailSenderFactory = javaMailSenderFactory;
  }

  public void notifyAdmin(String subject, String message) {
    sendMessage(adminEmail, subject, message);
  }

  public void notifyTasker(final String subject, final String message) {
    sendMessage(taskerEmail, subject, message);
  }

  public void notifyIKEA(final String subject, final String message) {
    sendMessage(ikeaEmail, subject, message);
  }

  private void sendMessage(final String to, final String subject, final String message) {
    final SimpleMailMessage msg = new SimpleMailMessage();
    msg.setTo(to);
    msg.setSubject(subject);
    msg.setText(message);
    javaMailSenderFactory.get().send(msg);
  }
}
