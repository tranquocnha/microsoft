package com.fpt.g52.carsharing.booking.infrastructure.repositories;

import com.fpt.g52.carsharing.booking.domain.model.aggregates.Booking;
import com.fpt.g52.carsharing.booking.domain.repositories.BookingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class BookingRepositoryImpl implements BookingRepository {

    private final BookingJpaRepository repository;

    public BookingRepositoryImpl(BookingJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Booking save(Booking entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<Booking> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Page<Booking> search(String query, Pageable pageable) {
        return repository.search(query, pageable);
    }

    @Override
    public  Optional<Booking> findByVehicleIdAndDuration(String id, Long from, Long to) {
        return repository.findByVehicleIdAndDuration(id, from, to);
    }

    @Override
    public Page<Booking> findByVehicleId(String id, Pageable pageable) {
        long currentTime = System.currentTimeMillis();
        return repository.findByVehicleId(id, currentTime, pageable);
    }
    
    @Override
    public Page<Booking> findByPaymentStatusAndAccount(String userId, Pageable pageable) {
        return repository.findByPaymentStatusAndAccount(userId, pageable);
    }

}
