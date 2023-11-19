package com.example.boilerplate.controller;

import com.example.boilerplate.exception.BadRequestException;
import com.example.boilerplate.models.UserEntity;
import com.example.boilerplate.repository.UserRepository;
import com.example.boilerplate.security.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

  private UserRepository userRepository;

  @GetMapping("/me")
  public UserEntity getCurrentUserEntity(@AuthenticationPrincipal UserPrincipal userPrincipal) {
    return userRepository.findByEmail(userPrincipal.getEmail())
      .orElseThrow(() -> new BadRequestException(String.format("Not existing user with email: %s", userPrincipal.getEmail())));
  }

}