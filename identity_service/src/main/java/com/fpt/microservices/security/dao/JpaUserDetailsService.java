package com.fpt.microservices.security.dao;

import com.fpt.microservices.security.model.UserSecurity;
import com.fpt.microservices.security.users.model.Users;
import com.fpt.microservices.security.users.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class JpaUserDetailsService implements UserDetailsService {
  
  private final UsersRepository usersRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return usersRepository.findByEmail(email).map(UserSecurity::new).orElseThrow(() -> new UsernameNotFoundException("User Not Found!"));
  }
  
  public Users getUserByEmail(String email) {
    Optional<Users> users = usersRepository.findByEmail(email);
    if(users.isEmpty()) return null;
    return users.get();
  }
}
