package com.fpt.microservices.security.users.service;

import com.fpt.microservices.security.users.model.Users;
import com.fpt.microservices.security.users.model.UsersRequest;
import com.fpt.microservices.security.users.repository.UsersRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsersServiceImpl implements UsersService {
  private final UsersRepository usersRepository;

  public List<Users> getAllUsers() {
    return usersRepository.findAll();
  }

  public Users addUser(UsersRequest user) {
    Users newUser = new Users();
    newUser.setFirstName(user.getFirstName());
    newUser.setLastName(user.getLastName());
    newUser.setEmail(user.getEmail());
    newUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
    newUser.setRoles(user.getRoles());
    return usersRepository.save(newUser);
  }
}
