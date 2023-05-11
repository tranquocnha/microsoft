package com.fpt.microservices.security.auth.service;

import com.fpt.microservices.security.users.model.Users;
import com.fpt.microservices.security.users.model.UsersRequest;

import java.util.Optional;

public interface AuthService {
  public Optional<Users> addUser(UsersRequest user);
}
