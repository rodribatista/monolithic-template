package com.example.boilerplate.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {

  private Auth auth;

  public record Auth(
    String tokenSecret,
    long tokenExpirationMsec,
    String issuer
  ) {}

}