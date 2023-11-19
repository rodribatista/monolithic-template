package com.example.boilerplate.config;

import com.example.boilerplate.security.AccessDeniedHandler;
import com.example.boilerplate.security.TokenAuthProvider;
import com.example.boilerplate.security.TokenAuthFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  private final TokenAuthFilter tokenAuthenticationFilter;
  private final TokenAuthProvider tokenAuthenticationProvider;
  private final AuthenticationEntryPoint authenticationEntryPoint;
  private final AccessDeniedHandler accessDeniedHandler;

  @Bean(BeanIds.AUTHENTICATION_MANAGER)
  public AuthenticationManager authenticationManager(
    AuthenticationConfiguration authenticationConfiguration
  ) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .cors(
        cors -> cors
          .configurationSource(corsConfigurationSource()))
      .csrf(csrf -> csrf.disable())
      .sessionManagement(
        sessionManagement -> sessionManagement
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .addFilterBefore(
        tokenAuthenticationFilter,
        UsernamePasswordAuthenticationFilter.class)
      .authorizeHttpRequests(
        authorizeRequests -> authorizeRequests
          .requestMatchers("/auth/**").permitAll()
          .anyRequest().authenticated())
      .exceptionHandling(
        exceptionHandling -> exceptionHandling
          .authenticationEntryPoint(authenticationEntryPoint)
          .accessDeniedHandler(accessDeniedHandler))
      .authenticationProvider(tokenAuthenticationProvider)
      .formLogin(formLogin -> formLogin.disable())
      .httpBasic(httpBasic -> httpBasic.disable());
    return http.build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowCredentials(true);
    // Todos los orígenes están permitidos, se debería restringir en producción.
    configuration.setAllowedOrigins(Arrays.asList("*"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setMaxAge(3600L);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

}