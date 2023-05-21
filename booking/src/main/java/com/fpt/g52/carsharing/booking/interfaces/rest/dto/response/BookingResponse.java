package com.fpt.g52.carsharing.booking.interfaces.rest.dto.response;

import com.fpt.g52.carsharing.booking.domain.model.aggregates.Booking;
import com.fpt.g52.carsharing.booking.domain.model.entities.User;
import com.fpt.g52.carsharing.booking.domain.model.entities.Vehicle;

import java.math.BigDecimal;

public record BookingResponse(
        String id,

        UserResponse account,

        VehicleResponse vehicle,

        String status,

        Long from,

        Long to,

        Long time,

        BigDecimal price,
        
        String paymentStatus
        
        
) {

    public BookingResponse(Booking booking) {
        this(
                booking.getId(),
                new UserResponse(booking.getAccount()),
                new VehicleResponse(booking.getVehicle()),
                booking.getStatus().toString(),
                booking.getDuration().getFrom(),
                booking.getDuration().getTo(),
                booking.getTime().getTime(),
                booking.getPrice().getPrice(),
                booking.getPaymentStatus().name()
        );
    }

    record UserResponse(String id, String name) {

        public UserResponse(User user) {
            this(user.getId(), user.getName());
        }
    }

    record VehicleResponse(String id, String name, BigDecimal price) {

        public VehicleResponse(Vehicle vehicle) {
            this(vehicle.getId(), vehicle.getName(), vehicle.getPricing());
        }
    }
}
