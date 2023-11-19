package com.example.boilerplate.security;

import com.example.boilerplate.exception.BadRequestException;
import com.example.boilerplate.models.UserEntity;
import com.example.boilerplate.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserEntity user = userRepository.findByEmail(email).orElseThrow(
      () -> new UsernameNotFoundException(String.format("Not existing user with email: %s", email)));
    return UserPrincipal.create(user);
  }

}