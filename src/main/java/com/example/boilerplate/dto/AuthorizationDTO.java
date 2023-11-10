package com.example.boilerplate.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorizationDTO {
  private String accessToken;
}