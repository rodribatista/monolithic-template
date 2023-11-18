package com.example.boilerplate.controller;

import com.example.boilerplate.dto.ApiResponseDTO;
import com.example.boilerplate.dto.AuthorizationDTO;
import com.example.boilerplate.dto.LoginDTO;
import com.example.boilerplate.dto.SignUpDTO;
import com.example.boilerplate.exception.BadRequestException;
import com.example.boilerplate.models.UserEntity;
import com.example.boilerplate.repository.UserRepository;
import com.example.boilerplate.security.TokenProvider;

import jakarta.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @PostMapping("/login")
  public ResponseEntity<AuthorizationDTO> authUser(@Valid @RequestBody LoginDTO loginDTO) {
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        loginDTO.getEmail(),
        loginDTO.getPassword()
      )
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String userId = userRepository.findByEmail(loginDTO.getEmail())
      .orElse(null).getId();
    AuthorizationDTO authorization = AuthorizationDTO.builder()
      .accessToken(tokenProvider.create(userId))
      .build();
    return ResponseEntity.ok(authorization);
  }

  @PostMapping("/signup")
  public ResponseEntity<ApiResponseDTO> registerUser(@Valid @RequestBody SignUpDTO signUpDTO) {
    if (userRepository.existsByEmail(signUpDTO.getEmail())) {
      throw new BadRequestException("Email address already in use.");
    }
    UserEntity user = UserEntity.builder()
      .name(signUpDTO.getName())
      .surname(signUpDTO.getSurname())
      .email(signUpDTO.getEmail())
      .password(passwordEncoder.encode(signUpDTO.getPassword()))
      .build();
    userRepository.save(user);
    ApiResponseDTO response = ApiResponseDTO.builder()
      .success(true)
      .status(HttpStatus.CREATED.name())
      .statusCode(HttpStatus.CREATED.value())
      .message("User registered successfully.")
      .build();
    return ResponseEntity.ok().body(response);
  }

}