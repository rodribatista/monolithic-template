package com.example.boilerplate.security;

import com.example.boilerplate.exception.GlobalExceptionHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@AllArgsConstructor
@Component
public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {

  private GlobalExceptionHandler resolver;

  @Override
  public void handle(
    HttpServletRequest request,
    HttpServletResponse response,
    AccessDeniedException exception
  ) throws IOException, ServletException {
    resolver.handleAccessDeniedException(exception, request);
  }

}