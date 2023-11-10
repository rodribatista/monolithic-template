package com.example.boilerplate.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;

@Getter
public class LoginDTO {

  @NotBlank
  @Email
  private String email;

  @NotBlank
  private String password;

}