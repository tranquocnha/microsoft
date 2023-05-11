package com.fpt.microservices.security.auth.service;

import com.fpt.microservices.security.users.model.Users;
import com.fpt.microservices.security.users.model.UsersRequest;
import com.fpt.microservices.security.users.repository.UsersRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
  
  private final UsersRepository usersRepository;

  public Optional<Users> addUser(UsersRequest user) {
    Users newUser = new Users();
    newUser.setFirstName(user.getFirst_name());
    newUser.setLastName(user.getLast_name());
    newUser.setEmail(user.getEmail());
    newUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
    newUser.setRoles("ROLE_USER");
    return Optional.of(usersRepository.save(newUser));
  }
}
