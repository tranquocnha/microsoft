package com.fpt.microservices.security.auth.response;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticationResponse {
  
  private String message;
  private String email;
  private String token;
  private List<GrantedAuthority> roles;
}
