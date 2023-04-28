package com.fpt.g52.microsoft.repository;

import com.fpt.g52.microsoft.model.VehiclesHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehiclesHourRepository extends JpaRepository<VehiclesHour,Integer> {
    List<VehiclesHour> findByStatus(Boolean status);
}
