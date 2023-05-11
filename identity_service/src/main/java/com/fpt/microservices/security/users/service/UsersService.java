package com.fpt.microservices.security.users.service;

import com.fpt.microservices.security.users.model.Users;
import com.fpt.microservices.security.users.model.UsersRequest;

import java.util.List;

public interface UsersService {
  public List<Users> getAllUsers();

  public Users addUser(UsersRequest user);
}
