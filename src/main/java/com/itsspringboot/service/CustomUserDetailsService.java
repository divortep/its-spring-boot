package com.itsspringboot.service;

import com.itsspringboot.model.User;
import com.itsspringboot.model.UserPrincipal;
import com.itsspringboot.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  UserRepository userRepository;

  public CustomUserDetailsService(final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public UserDetails loadUserByUsername(final String usernameOrEmail) throws UsernameNotFoundException {
    User user = userRepository.findByUsernameOrEmail(usernameOrEmail)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail));

    return new UserPrincipal(user);
  }

  public UserDetails loadUserById(final String id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + id));

    return new UserPrincipal(user);
  }
}
