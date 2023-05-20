package com.fpt.g52.carsharing.booking.infrastructure.repositories;

import com.fpt.g52.carsharing.booking.domain.model.aggregates.Booking;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingJpaRepository extends JpaRepository<Booking, String> {

    @Query(
            "select b from Booking b"
            + " where b.id like %:query%"
            + " or b.vehicle.id like %:query%"
            + " or b.vehicle.name like %:query%"
            + " order by b.time.time desc"
    )
    Page<Booking> search(String query, Pageable pageable);
    
    @Query(
            "select b from Booking b"
            + " where b.vehicle.id = :id"
            + " AND (b.duration.from between :from and :to"
            + " or b.duration.to between  :from and :to)"
            + " AND b.status IN ('BOOKED', 'RECEIVED')"
    )
    Optional<Booking> findByVehicleIdAndDuration(String id, Long from, Long to);
    
    @Query(
            "select b from Booking b"
            + " where b.vehicle.id = :id"
            + " AND b.status IN ('BOOKED', 'RECEIVED')"
            + " AND b.duration.to > :curTime "
            + " order by b.time.time desc"
    )
    Page<Booking> findByVehicleId(String id, long curTime, Pageable pageable);
}
