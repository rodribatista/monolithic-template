package com.example.boilerplate.security;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class TokenAuthProvider implements AuthenticationProvider {

  private final UserDetailsServiceImpl userDetailsService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = authentication.getName();
    String password = String.valueOf(authentication.getCredentials());
    UserDetails userDetails = userDetailsService.loadUserByUsername(String.valueOf(username));
    if (userDetails != null) {
      if (passwordEncoder.matches(String.valueOf(password), userDetails.getPassword())) {
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
      }
    }
    throw new BadCredentialsException("Bad credentials.");
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }

}