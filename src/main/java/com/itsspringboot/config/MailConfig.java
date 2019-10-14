package com.itsspringboot.config;

import com.itsspringboot.exception.AppException;
import com.itsspringboot.model.UserPrincipal;
import com.itsspringboot.security.AESCipher;
import java.util.Optional;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class MailConfig {

  @Value("${aes.key}")
  private String aesKey;

  private JavaMailSender mailSender;

  @Autowired
  public void setMailSender(final JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  @Bean
  public Supplier<JavaMailSender> mailSenderSupplier() {
    return this::getJavaMailSender;
  }

  private JavaMailSender getJavaMailSender() {
    Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
        .filter(auth -> auth.getPrincipal() instanceof UserPrincipal)
        .map(auth -> (UserPrincipal) auth.getPrincipal())
        .ifPresent(userPrincipal -> {
          ((JavaMailSenderImpl) mailSender).setUsername(userPrincipal.getUser().getEmail());
          ((JavaMailSenderImpl) mailSender).setPassword(extractPassword(userPrincipal.getPassword()));
        });

    return mailSender;
  }


  private String extractPassword(final String passwordStr) {
    try {
      return AESCipher.decrypt(passwordStr, aesKey);
    } catch (Exception e) {
      throw new AppException(e.getMessage(), e);
    }
  }
}
