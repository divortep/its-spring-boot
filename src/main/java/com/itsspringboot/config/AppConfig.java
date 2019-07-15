package com.itsspringboot.config;

import com.google.common.collect.ImmutableList;
import com.itsspringboot.model.Role;
import com.itsspringboot.model.User;
import com.itsspringboot.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

  @Value("${its.admin.username}")
  private String adminUsername;

  @Value("${its.admin.email}")
  private String adminEmail;

  @Value("${its.admin.defaultPassword}")
  private String adminDefaultPassword;

  private UserRepository userRepository;

  @Autowired
  public AppConfig(final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @EventListener(ApplicationReadyEvent.class)
  private void createAdminUser() {
    final Optional<User> admin = userRepository.findByUsernameOrEmail("admin");
    if (!admin.isPresent()) {
      final User adminUser = new User(adminUsername, adminUsername, adminEmail, adminDefaultPassword,
          ImmutableList.of(Role.ADMIN));
      userRepository.saveUser(adminUser);
    }
  }
}
