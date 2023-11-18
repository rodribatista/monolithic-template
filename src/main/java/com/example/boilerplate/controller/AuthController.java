package com.example.boilerplate.controller;

import com.example.boilerplate.dto.ApiResponse;
import com.example.boilerplate.dto.TokenResponse;
import com.example.boilerplate.dto.LoginRequest;
import com.example.boilerplate.dto.SignUpRequest;

import com.example.boilerplate.service.AuthService;
import jakarta.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

  AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<TokenResponse> authUser(
    @Valid @RequestBody LoginRequest loginDTO) {
    return ResponseEntity.ok(authService.authUser(loginDTO));
  }

  @PostMapping("/signup")
  public ResponseEntity<ApiResponse> registerUser(
    @Valid @RequestBody SignUpRequest signUpDTO) {
    return ResponseEntity.ok(authService.registerUser(signUpDTO));
  }

}