package com.fpt.microservices.security.users.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsersRequest {
  
  public UsersRequest() { }
  
  public UsersRequest(String firstName, String lastName, String email, String password, String roles) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.roles = roles;
  }
  
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private String roles;
}
