package g52.training.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public final class JwtUtils {

  private JwtUtils() throws IOException {

  }

  static {
//    File resource = null;
    try {
      File resource = new ClassPathResource(
              "certs/public.pem").getFile();
      String key = new String(
              Files.readAllBytes(resource.toPath()));
//      System.out.println(key);
//      System.out.println("OK");
      String publicKeyPEM = key
              .replace("-----BEGIN PUBLIC KEY-----", "")
              .replaceAll(System.lineSeparator(), "")
              .replace("-----END PUBLIC KEY-----", "");

      byte[] encoded = Base64.decodeBase64(publicKeyPEM);

      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
      publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    } catch (InvalidKeySpecException e) {
      throw new RuntimeException(e);
    }

  }

//  private static String SECRET_KEY = "secret";
//
//  public static String extractUsername(String token) {
//    return extractClaim(token, Claims::getSubject);
//  }
//
//  public static Date extractExpiration(String token) {
//    return extractClaim(token, Claims::getExpiration);
//  }
//
//  public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//    final Claims claims = extractAllClaims(token);
//    return claimsResolver.apply(claims);
//  }
//
//  private static Claims extractAllClaims(String token) {
//    return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
//  }
//
//  public static Boolean isTokenExpired(String token) {
//    return extractExpiration(token).before(new Date());
//  }
//
//  private static String createToken(Map<String, Object> claims, String subject) {
//
//    return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
//        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
//        .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
//  }

  private static RSAPublicKey publicKey;

  public static Boolean validateToken(String token) {
    return !isTokenExpired(token);
  }

  private static PublicKey getPublicKey() {
    return publicKey;
  }

  public static String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  private static Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private static Claims extractAllClaims(String token) {
    return Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(token).getBody();
  }

  private static Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

}
