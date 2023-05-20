package com.fpt.g52.carsharing.booking.domain.repositories;

import com.fpt.g52.carsharing.booking.domain.model.aggregates.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookingRepository {

    Booking save(Booking entity);

    Optional<Booking> findById(String id);

    Page<Booking> search(String query, Pageable pageable);
    
    Optional<Booking> findByVehicleIdAndDuration(String id, Long from, Long to);
    
    Page<Booking> findByVehicleId(String id, Pageable pageable);
}
