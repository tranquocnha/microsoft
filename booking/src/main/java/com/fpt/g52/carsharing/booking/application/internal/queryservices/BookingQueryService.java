package com.fpt.g52.carsharing.booking.application.internal.queryservices;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fpt.g52.carsharing.booking.domain.exceptions.ResourceInvalidException;
import com.fpt.g52.carsharing.booking.domain.model.aggregates.Booking;
import com.fpt.g52.carsharing.booking.domain.repositories.BookingRepository;

@Service
public class BookingQueryService {

    private final BookingRepository repository;

    public BookingQueryService(BookingRepository repository) {
        this.repository = repository;
    }

    public Booking findById(String id, String userId) {
        return repository.findById(id).filter(item -> item.getAccount().getId().equals(userId)).orElseThrow(() -> new ResourceInvalidException("Booking had not exists"));
    }

    public Page<Booking> search(String query, Pageable pageable) {
        return repository.search(query, pageable);
    }
    
    public Page<Booking> findByVehicleId(String id, Pageable pageable) {
        return repository.findByVehicleId(id, pageable);
    }
    
    public Page<Booking> findByPaymentStatusAndAccount(String userId, Pageable pageable) {
        return repository.findByPaymentStatusAndAccount(userId, pageable);
    }
}
