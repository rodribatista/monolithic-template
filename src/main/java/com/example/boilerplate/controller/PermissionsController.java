package com.example.boilerplate.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PermissionsController {

  @GetMapping("/read")
  @PreAuthorize("hasAuthority('READ')")
  public ResponseEntity<String> readPrivileges() {
    return ResponseEntity.ok("Hello with read privileges");
  }

  @GetMapping("/write")
  @PreAuthorize("hasAuthority('WRITE')")
  public ResponseEntity<String> writePrivileges() {
    return ResponseEntity.ok("Hello with write privileges");
  }

  @GetMapping("/delete")
  @PreAuthorize("hasAuthority('DELETE')")
  public ResponseEntity<String> deletePrivileges() {
    return ResponseEntity.ok("Hello with delete privileges");
  }

}