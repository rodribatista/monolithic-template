package com.example.boilerplate.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.example.boilerplate.config.AppProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class TokenProvider {

  private final AppProperties appProperties;
  private final Algorithm signAlgorithm;

  public TokenProvider(AppProperties appProperties) {
    this.appProperties = appProperties;
    this.signAlgorithm = Algorithm.HMAC512(appProperties.getAuth().tokenSecret());
  }

  public String create(String email) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + appProperties.getAuth().tokenExpirationMsec());
    String token = null;
    try {
      token = JWT.create()
        .withSubject(email)
        .withIssuedAt(now)
        .withExpiresAt(expiryDate) // TODO Set expiration date
        .withIssuer(appProperties.getAuth().issuer())
        .sign(signAlgorithm);
    } catch (JWTCreationException exception) {
      log.error("Claims couldn't be converted to JSON.");
    }
    return token;
  }

}