package com.fpt.microservices.security.users.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.microservices.security.users.model.Users;
import com.fpt.microservices.security.users.model.UsersRequest;
import com.fpt.microservices.security.users.service.UsersService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UsersController {

  private final UsersService usersService;

  // @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
  @GetMapping("/get-all")
  public List<Users> getUsers() {
    return usersService.getAllUsers();
  }

  // @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
  @PostMapping("/create")
  public Users createUsers(@RequestBody UsersRequest user) {
    return usersService.addUser(user);
  }

}
