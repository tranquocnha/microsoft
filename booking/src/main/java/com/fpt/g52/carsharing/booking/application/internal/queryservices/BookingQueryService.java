package com.fpt.g52.carsharing.booking.application.internal.queryservices;

import com.fpt.g52.carsharing.booking.domain.model.aggregates.Booking;
import com.fpt.g52.carsharing.booking.domain.exceptions.NotFoundException;
import com.fpt.g52.carsharing.booking.domain.repositories.BookingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookingQueryService {

    private final BookingRepository repository;

    public BookingQueryService(BookingRepository repository) {
        this.repository = repository;
    }

    public Booking findById(String id) {
        return repository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Page<Booking> search(String query, Pageable pageable) {
        return repository.search(query, pageable);
    }
    
    public Page<Booking> findByVehicleId(String id, Pageable pageable) {
        return repository.findByVehicleId(id, pageable);
    }
}
