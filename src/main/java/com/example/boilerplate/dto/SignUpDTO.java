package com.example.boilerplate.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;

@Getter
public class SignUpDTO {

  private String name;

  private String surname;

  @NotBlank
  @Email
  private String email;

  @NotBlank
  private String password;

}