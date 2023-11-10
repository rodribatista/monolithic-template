package com.example.boilerplate.exception;

import com.example.boilerplate.dto.ErrorDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@AllArgsConstructor
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  private static String INTERNAL_SERVER_ERROR_MESSAGE = "An internal server error occured.";

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<?> runtimeExceptionHandler(RuntimeException e, HttpServletRequest req) {
    String referenceCode = UUID.randomUUID().toString();
    if (e instanceof BadRequestException) {
      log.error("{} Bad request exception: {}", referenceCode, e.getMessage());
      return ResponseEntity.badRequest().body(
        ErrorDTO.builder()
          .referenceCode(referenceCode)
          .status(HttpStatus.BAD_REQUEST.name())
          .statusCode(HttpStatus.BAD_REQUEST.value())
          .message(e.getMessage())
          .requestMethod(req.getMethod())
          .requestUrl(String.valueOf(req.getRequestURL()))
          .build());
    } else if (e instanceof BadCredentialsException) {
      log.error("{} Bad credentials exception: {}", referenceCode, e.getMessage());
      return ResponseEntity.badRequest().body(
        ErrorDTO.builder()
          .referenceCode(referenceCode)
          .status(HttpStatus.BAD_REQUEST.name())
          .statusCode(HttpStatus.BAD_REQUEST.value())
          .message(e.getMessage())
          .requestMethod(req.getMethod())
          .requestUrl(String.valueOf(req.getRequestURL()))
          .build());
    } else if (e instanceof ResourceNotFoundException){
      log.error("{} Resource not found exception: {}", referenceCode, e.getMessage());
      return new ResponseEntity<>(
        ErrorDTO.builder()
          .referenceCode(referenceCode)
          .status(HttpStatus.NOT_FOUND.name())
          .statusCode(HttpStatus.NOT_FOUND.value())
          .message(e.getMessage())
          .requestMethod(req.getMethod())
          .requestUrl(String.valueOf(req.getRequestURL()))
          .build(), HttpStatus.NOT_FOUND);
    }
    log.error("{} Internal server error: {}", referenceCode, e.getMessage());
    return ResponseEntity.internalServerError().body(
      ErrorDTO.builder()
          .referenceCode(referenceCode)
          .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
          .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
          .message(INTERNAL_SERVER_ERROR_MESSAGE)
          .requestMethod(req.getMethod())
          .requestUrl(String.valueOf(req.getRequestURL()))
          .build());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> exceptionHandler(Exception e, HttpServletRequest req) {
    String referenceCode = UUID.randomUUID().toString();
    log.error("{} Internal server error: {}", referenceCode, e.getMessage());
    return ResponseEntity.internalServerError().body(
      ErrorDTO.builder()
          .referenceCode(referenceCode)
          .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
          .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
          .message(INTERNAL_SERVER_ERROR_MESSAGE)
          .requestMethod(req.getMethod())
          .requestUrl(String.valueOf(req.getRequestURL()))
          .build());
  }

}