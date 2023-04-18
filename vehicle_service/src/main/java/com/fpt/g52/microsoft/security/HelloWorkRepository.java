package com.fpt.g52.microsoft.security;

import com.fpt.g52.microsoft.model.HelloWorkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HelloWorkRepository extends JpaRepository<HelloWorkEntity, Integer> {
}
