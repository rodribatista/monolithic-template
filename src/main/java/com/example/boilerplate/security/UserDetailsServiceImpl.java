package com.example.boilerplate.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Map<String, String> users = Map.of("test@mail.com", passwordEncoder.encode("password"));
    if (!users.containsKey(email)) {
      throw new UsernameNotFoundException(String.format("User not found with email: %s", email));
    }
    return new User(email, users.get(email), new ArrayList<>());
  }

}