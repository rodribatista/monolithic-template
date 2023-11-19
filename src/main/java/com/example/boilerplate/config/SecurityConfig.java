package com.example.boilerplate.config;

import com.example.boilerplate.security.TokenAuthenticationFilter;
import com.example.boilerplate.security.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

  private final UserDetailsServiceImpl userDetailsService;
  private final PasswordEncoder passwordEncoder;
  private final TokenAuthenticationFilter tokenAuthenticationFilter;

  @Bean(BeanIds.AUTHENTICATION_MANAGER)
  public AuthenticationManager authenticationManager(
    AuthenticationConfiguration authenticationConfiguration
  ) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Autowired
  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .cors(
        cors -> cors.configurationSource(
          request -> {
            var corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
            corsConfiguration.setAllowCredentials(true);
            corsConfiguration.addAllowedOriginPattern("*");
            corsConfiguration.addAllowedHeader("*");
            corsConfiguration.addAllowedMethod("*");
            corsConfiguration.setMaxAge(3600L);
            return corsConfiguration;
          }
        )
      )
      .csrf(csrf -> csrf.disable())
      .sessionManagement(
        sessionManagement -> sessionManagement
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      )
      .addFilterBefore(
        tokenAuthenticationFilter,
        UsernamePasswordAuthenticationFilter.class
      )
      .authorizeHttpRequests(
        authorizeRequests -> authorizeRequests
          .requestMatchers("/auth/**").permitAll()
          .anyRequest().authenticated()
      )
      .formLogin(formLogin -> formLogin.disable())
      .httpBasic(httpBasic -> httpBasic.disable());
    return http.build();
  }

}