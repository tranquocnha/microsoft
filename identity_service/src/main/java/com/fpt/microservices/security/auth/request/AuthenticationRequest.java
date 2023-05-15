package com.fpt.microservices.security.auth.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticationRequest {
  private String email;
  private String password;
  private String token;
}
