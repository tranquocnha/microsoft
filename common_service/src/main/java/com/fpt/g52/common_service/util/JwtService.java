package com.fpt.g52.common_service.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtService {

  private RSAPublicKey publicKey;

  public JwtService() {
  }

  public JwtService(String publicKeyFile) throws Exception {
    InputStream inputStream = getFileFromResourceAsStream(publicKeyFile);
    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    String line;
    StringBuilder strKeyPEM = new StringBuilder();
    while ((line = br.readLine()) != null) {
      strKeyPEM.append(line).append("\n");
    }
    br.close();
    String key = strKeyPEM.toString();

    String publicKeyPEM = key
        .replace("-----BEGIN PUBLIC KEY-----", "")
        .replaceAll(System.lineSeparator(), "")
        .replace("-----END PUBLIC KEY-----", "");

    byte[] encoded = Base64.decodeBase64(publicKeyPEM);

    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);

    this.publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
  }

  public Boolean validateToken(String token) {
    if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
      token = token.substring(7);
    }

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

  private InputStream getFileFromResourceAsStream(String fileName) {

    ClassLoader classLoader = getClass().getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream(fileName);

    if (inputStream == null) {
      throw new IllegalArgumentException("file not found! " + fileName);
    } else {
      return inputStream;
    }
  }
}
