package com.fpt.g52.common_service.util;

import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtService {

  @Value("classpath:certs/public.pem") 
  private RSAPublicKey publicKey;
  
  public Boolean validateToken(String token) {
    return !isTokenExpired(token);
  }

  private PublicKey getPublicKey() {
    return publicKey;
  }
  
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }
  
  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(token).getBody();
  }

  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }
}
