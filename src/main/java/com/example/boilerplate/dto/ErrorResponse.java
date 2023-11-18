package com.example.boilerplate.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ErrorResponse implements Serializable {
  private String referenceCode;
  private String status;
  private int statusCode;
  private String message;
  private String requestMethod;
  private String requestUrl;
}