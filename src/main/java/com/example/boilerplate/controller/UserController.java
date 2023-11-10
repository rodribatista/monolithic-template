package com.example.boilerplate.controller;

import com.example.boilerplate.exception.ResourceNotFoundException;
import com.example.boilerplate.models.UserEntity;
import com.example.boilerplate.repository.UserRepository;
import com.example.boilerplate.security.TokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

  private UserRepository userRepository;
  private TokenProvider tokenProvider;

  @GetMapping("/me")
  public UserEntity getCurrentUserEntity(Authentication authentication) {
    return userRepository.findByEmail(authentication.getName())
      .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + authentication.getName()));
  }

}