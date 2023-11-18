package com.example.boilerplate.service;

import com.example.boilerplate.dto.ApiResponse;
import com.example.boilerplate.dto.LoginRequest;
import com.example.boilerplate.dto.SignUpRequest;
import com.example.boilerplate.dto.TokenResponse;
import com.example.boilerplate.exception.BadRequestException;
import com.example.boilerplate.models.UserEntity;
import com.example.boilerplate.repository.UserRepository;
import com.example.boilerplate.security.TokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final TokenProvider tokenProvider;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public TokenResponse authUser(LoginRequest loginDTO) {
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        loginDTO.getEmail(),
        loginDTO.getPassword()
      )
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String userId = userRepository.findByEmail(loginDTO.getEmail())
      .orElse(null).getId();
    return TokenResponse.builder()
      .accessToken(tokenProvider.create(userId))
      .build();
  }

  public ApiResponse registerUser(SignUpRequest signUpDTO) {
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
    return ApiResponse.builder()
      .success(true)
      .status(HttpStatus.CREATED.name())
      .statusCode(HttpStatus.CREATED.value())
      .message("User registered successfully.")
      .build();
  }

}