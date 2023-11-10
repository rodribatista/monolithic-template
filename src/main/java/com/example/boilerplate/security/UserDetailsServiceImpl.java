package com.example.boilerplate.security;

import com.example.boilerplate.models.UserEntity;
import com.example.boilerplate.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserEntity user = userRepository.findByEmail(email).orElseThrow(
      () -> new UsernameNotFoundException(String.format("User not found with email: %s", email)));
    return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
  }

}