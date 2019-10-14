package com.itsspringboot.service;

import com.itsspringboot.model.Performer;
import com.itsspringboot.model.User;
import com.itsspringboot.model.UserPrincipal;
import com.itsspringboot.model.UserSettings;
import java.util.Optional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  public Optional<User> getCurrentUser() {
    return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
        .filter(auth -> auth.getPrincipal() instanceof UserPrincipal)
        .map(auth -> (UserPrincipal) auth.getPrincipal())
        .map(UserPrincipal::getUser);
  }

}
