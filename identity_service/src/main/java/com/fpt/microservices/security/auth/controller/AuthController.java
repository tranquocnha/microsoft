package com.fpt.microservices.security.auth.controller;

import com.fpt.microservices.security.auth.request.AuthenticationRequest;
import com.fpt.microservices.security.auth.response.AuthenticationResponse;
import com.fpt.microservices.security.auth.service.AuthService;
import com.fpt.microservices.security.config.JwtUtils;
import com.fpt.microservices.security.dao.JpaUserDetailsService;
import com.fpt.microservices.security.model.UserSecurity;
import com.fpt.microservices.security.users.model.UsersRequest;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationManager authenticationManager;

  private final JpaUserDetailsService jpaUserDetailsService;

  private final AuthService authService;

  private final JwtUtils jwtUtils;

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request, HttpServletResponse response) {
    AuthenticationResponse res = new AuthenticationResponse();
    try {
      authenticationManager
      .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword(),
          new ArrayList<>()));
      final UserDetails user = jpaUserDetailsService.loadUserByUsername(request.getEmail());
      if (user != null) {
        String jwt = jwtUtils.generateToken(user);
        Cookie cookie = new Cookie("jwt", jwt);
        cookie.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days
        cookie.setHttpOnly(true);
        cookie.setPath("/"); // Global
        response.addCookie(cookie);
        System.out.println(jwt);
        res.setEmail(user.getUsername());
        res.setToken(jwt);
       // res.setRoles(user.getAuthorities());
        return ResponseEntity.ok(res);
      }
      res.setMessage("Error authenticating");
      return ResponseEntity.status(400).body(res);
    } catch (Exception e) {
      res.setMessage(e.getMessage());
      return ResponseEntity.status(400).body(res);
    }
  }

  @PostMapping("/register")
  public ResponseEntity<UserSecurity> register(@RequestBody UsersRequest user) throws Exception {
    return ResponseEntity.ok(authService.addUser(user).map(UserSecurity::new).orElseThrow(() -> new Exception("Unknown")));
  }

}
