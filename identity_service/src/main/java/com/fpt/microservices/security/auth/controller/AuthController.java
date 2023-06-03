package com.fpt.microservices.security.auth.controller;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.microservices.security.auth.request.AuthenticationRequest;
import com.fpt.microservices.security.auth.service.AuthService;
import com.fpt.microservices.security.config.JwtUtils;
import com.fpt.microservices.security.dao.JpaUserDetailsService;
import com.fpt.microservices.security.model.UserSecurity;
import com.fpt.microservices.security.users.model.Users;
import com.fpt.microservices.security.users.model.UsersRequest;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationManager authenticationManager;

  private final JpaUserDetailsService jpaUserDetailsService;

  private final AuthService authService;

  private final JwtUtils jwtUtils;
  
  //@PostConstruct
  private void postConstruct() throws Exception {
    register(new UsersRequest("Admin", "Admin", "admin@fpt.com", "admin", "ROLE_ADMIN"));
    register(new UsersRequest("Uesr", "User", "user@fpt.com", "user", "ROLE_USER"));
    register(new UsersRequest("Manager", "Manager", "manager@fpt.com", "manager", "ROLE_ADMIN,ROLE_USER"));
  }

  @PostMapping("/login")
  public String authenticate(@RequestBody AuthenticationRequest request, HttpServletResponse response) {
    try {
      authenticationManager
      .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword(), new ArrayList<>()));
      final UserDetails user = jpaUserDetailsService.loadUserByUsername(request.getEmail());
      if (user != null) {
        String jwt = jwtUtils.generateToken(user);
        Cookie cookie = new Cookie("jwt", jwt);
        cookie.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return jwt;
      }
      return "Error authenticating";
    } catch (Exception e) {
      return e.getMessage();
    }
  }

  @PostMapping("/register")
  public ResponseEntity<UserSecurity> register(@RequestBody UsersRequest user) throws Exception {
    return ResponseEntity.ok(authService.addUser(user).map(UserSecurity::new).orElseThrow(() -> new Exception("Unknown")));
  }

  @PostMapping("/validate-token")
  public ResponseEntity<Boolean> validateToken(@RequestParam("token") String token) {
    try {
      String email = jwtUtils.extractUsername(token);
      final UserDetails user = jpaUserDetailsService.loadUserByUsername(email);
      boolean isExpired = jwtUtils.validateToken(token, user);
      return ResponseEntity.ok(isExpired);
    } catch(Exception e) {
      return ResponseEntity.status(400).body(false);
    }
  }
  
  @GetMapping("/v1/validate-token")
  public ResponseEntity<Boolean> validateTokenV1(@RequestParam("token") String token) {
    try {
      String email = jwtUtils.extractUsername(token);
      final UserDetails user = jpaUserDetailsService.loadUserByUsername(email);
      boolean isExpired = jwtUtils.validateToken(token, user);
      return ResponseEntity.ok(isExpired);
    } catch(Exception e) {
      return ResponseEntity.status(400).body(false);
    }
  }
  
  @GetMapping("/v1/user")
  public ResponseEntity<String> getUserId(@RequestParam("token") String token) {
    try {
      String email = jwtUtils.extractUsername(token);
      final UserDetails user = jpaUserDetailsService.loadUserByUsername(email);
      boolean isExpired = jwtUtils.validateToken(token, user);
      return ResponseEntity.ok(isExpired ? user.getUsername() : null);
    } catch(Exception e) {
      return ResponseEntity.status(400).body(null);
    }
  }
  
  @GetMapping("/get-user-by-token")
  public ResponseEntity<Object> getUserByToken(@RequestParam("token") String token) {
    try {
      String email = jwtUtils.extractUsername(token);
      final UserDetails user = jpaUserDetailsService.loadUserByUsername(email);
      boolean isExpired = jwtUtils.validateToken(token, user);
      if(isExpired) {
        Users userEntity = jpaUserDetailsService.getUserByEmail(email);
        return ResponseEntity.ok(userEntity); 
      } else {
        return ResponseEntity.ok(false); 
      }
    } catch(Exception e) {
      return ResponseEntity.status(400).body(null);
    }
  }
}
