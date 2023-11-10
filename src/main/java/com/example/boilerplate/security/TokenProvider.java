package com.example.boilerplate.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
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

  public String create(String id) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + appProperties.getAuth().tokenExpirationMsec());
    String token = null;
    try {
      token = JWT.create()
        .withIssuer(appProperties.getAuth().issuer())
        .withIssuedAt(now)
        .withExpiresAt(expiryDate) // TODO Set expiration date
        .withClaim("usr", id)
        .sign(signAlgorithm);
    } catch (JWTCreationException exception) {
      log.error("Claims couldn't be converted to JSON.");
    }
    return token;
  }

  public boolean validate(String token) {
    try {
      JWTVerifier verifier = JWT.require(signAlgorithm)
        .withIssuer(appProperties.getAuth().issuer())
        .withClaimPresence("iat")
        .withClaimPresence("exp")
        .withClaimPresence("usr")
        .build();
      verifier.verify(token);
      return true;
    } catch (JWTVerificationException exception) {
      log.error("Invalid token.");
    }
    return false;
  }

  public String getUserId(String token) {
    DecodedJWT jwt = null;
    try {
      jwt = JWT.decode(token);
    } catch (JWTDecodeException exception) {
      log.error("Invalid token.");
    }
    return jwt.getClaim("usr").asString();
  }

}