package com.example.boilerplate.controller;

import com.example.boilerplate.dto.AuthorizationDTO;
import com.example.boilerplate.dto.LoginDTO;
import com.example.boilerplate.security.TokenProvider;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final TokenProvider tokenProvider;

  @PostMapping("/login")
  public ResponseEntity<?> authUser(@Valid @RequestBody LoginDTO loginDTO) {
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        loginDTO.getEmail(),
        loginDTO.getPassword()
      )
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    AuthorizationDTO authorization = AuthorizationDTO.builder()
      .accessToken(tokenProvider.create(loginDTO.getEmail()))
      .build();
    return ResponseEntity.ok(authorization);
  }

}