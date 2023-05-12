package com.fpt.g52.microsoft.repository;

import com.fpt.g52.microsoft.model.Hour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HourRepository extends JpaRepository<Hour, Integer> {
}
