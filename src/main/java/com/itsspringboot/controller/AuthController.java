package com.itsspringboot.controller;

import com.google.common.collect.ImmutableList;
import com.itsspringboot.exception.AppException;
import com.itsspringboot.model.Role;
import com.itsspringboot.model.User;
import com.itsspringboot.payload.ApiResponse;
import com.itsspringboot.payload.JwtAuthResponse;
import com.itsspringboot.payload.LoginRequest;
import com.itsspringboot.payload.RegisterRequest;
import com.itsspringboot.repository.UserRepository;
import com.itsspringboot.security.JwtTokenProvider;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/auth")
public class AuthController {

  AuthenticationManager authenticationManager;
  UserRepository userRepository;
  PasswordEncoder passwordEncoder;
  JwtTokenProvider tokenProvider;

  @Autowired
  public AuthController(final AuthenticationManager authenticationManager, final UserRepository userRepository,
                        final PasswordEncoder passwordEncoder, final JwtTokenProvider tokenProvider) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.tokenProvider = tokenProvider;
  }

  @PostMapping("/login")
  public ResponseEntity<JwtAuthResponse> authenticateUser(@Valid @RequestBody final LoginRequest loginRequest) {

    final UsernamePasswordAuthenticationToken token =
        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
    final Authentication authentication = authenticationManager.authenticate(token);

    SecurityContextHolder.getContext().setAuthentication(authentication);
    final String jwt = tokenProvider.generateToken(authentication);
    return ResponseEntity.ok(new JwtAuthResponse(jwt));
  }

  @Secured("ADMIN")
  @PostMapping("/register")
  public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody final RegisterRequest signUpRequest) {
    final String password = passwordEncoder.encode(signUpRequest.getPassword());
    final User userToSave = new User(signUpRequest.getName(), signUpRequest.getUsername(),
        signUpRequest.getEmail(), password, ImmutableList.of(Role.USER));

    userRepository.saveUser(userToSave)
        .orElseThrow(() -> new AppException("An error occurred while saving a user."));

    return ResponseEntity.ok(new ApiResponse(true, "User registered successfully"));
  }

  @GetMapping("/encode")
  @ResponseBody
  public String encodePassword(final String password) {
    return passwordEncoder.encode(password);
  }
}
