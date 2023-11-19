package com.example.boilerplate.exception;

import com.example.boilerplate.dto.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.UUID;

@AllArgsConstructor
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private static String INTERNAL_SERVER_ERROR_MESSAGE = "An internal server error occured.";

  @ExceptionHandler({AuthenticationException.class})
  public ResponseEntity<ErrorResponse> handleAuthenticationException(Exception e, HttpServletRequest req) {
    return newHandledExceptionResponse(e, HttpStatus.UNAUTHORIZED, req);
  }

  @ExceptionHandler({AccessDeniedException.class})
  public ResponseEntity<ErrorResponse> handleAccessDeniedException(Exception e, HttpServletRequest req) {
    return newHandledExceptionResponse(e, HttpStatus.UNAUTHORIZED, req);
  }

  @ExceptionHandler({UsernameNotFoundException.class})
  public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(Exception e, HttpServletRequest req) {
    return newHandledExceptionResponse(e, HttpStatus.BAD_REQUEST, req);
  }

  @ExceptionHandler({BadRequestException.class})
  public ResponseEntity<ErrorResponse> handleBadRequestException(Exception e, HttpServletRequest req) {
    return newHandledExceptionResponse(e, HttpStatus.BAD_REQUEST, req);
  }

  @ExceptionHandler({ResourceNotFoundException.class})
  public ResponseEntity<ErrorResponse> handleResourceNotFoundException(Exception e, HttpServletRequest req) {
    return newHandledExceptionResponse(e, HttpStatus.NOT_FOUND, req);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> exceptionHandler(Exception e, HttpServletRequest req) {
    String referenceCode = UUID.randomUUID().toString();
    log.error("{} {}: {}", referenceCode, e.getClass().getName(),e.getMessage());
    e.printStackTrace();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
      ErrorResponse.builder()
        .referenceCode(referenceCode)
        .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .message(INTERNAL_SERVER_ERROR_MESSAGE)
        .requestMethod(req.getMethod())
        .requestUrl(String.valueOf(req.getRequestURL()))
        .build());
  }

  private ResponseEntity<ErrorResponse> newHandledExceptionResponse(
    Exception e,
    HttpStatus status,
    HttpServletRequest req
  ) {
    String referenceCode = UUID.randomUUID().toString();
    log.error("{} {}: {}", referenceCode, e.getClass().getName(),e.getMessage());
    return ResponseEntity.status(status).body(
      ErrorResponse.builder()
        .referenceCode(referenceCode)
        .status(status.name())
        .statusCode(status.value())
        .message(e.getMessage())
        .requestMethod(req.getMethod())
        .requestUrl(String.valueOf(req.getRequestURL()))
        .build());
  }

}