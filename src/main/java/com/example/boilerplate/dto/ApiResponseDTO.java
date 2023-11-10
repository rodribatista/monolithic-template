package com.example.boilerplate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ApiResponseDTO {
  private boolean success;
  private String status;
  private int statusCode;
  private String message;
}