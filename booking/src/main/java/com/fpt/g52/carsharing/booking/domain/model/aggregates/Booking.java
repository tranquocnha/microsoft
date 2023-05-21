package com.fpt.g52.carsharing.booking.domain.model.aggregates;

import java.math.BigDecimal;

import org.springframework.data.domain.AbstractAggregateRoot;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.fpt.g52.carsharing.booking.domain.model.commands.BookingCommand;
import com.fpt.g52.carsharing.booking.domain.model.entities.User;
import com.fpt.g52.carsharing.booking.domain.model.entities.Vehicle;
import com.fpt.g52.carsharing.booking.domain.model.event.BookingCompletedEvent;
import com.fpt.g52.carsharing.booking.domain.model.event.CarBookedEvent;
import com.fpt.g52.carsharing.booking.domain.model.event.CarReceivedEvent;
import com.fpt.g52.carsharing.booking.domain.model.event.PaymentCompleteEvent;
import com.fpt.g52.carsharing.booking.domain.model.valueobjects.BookingDuration;
import com.fpt.g52.carsharing.booking.domain.model.valueobjects.BookingPrice;
import com.fpt.g52.carsharing.booking.domain.model.valueobjects.BookingStatus;
import com.fpt.g52.carsharing.booking.domain.model.valueobjects.BookingTime;
import com.fpt.g52.carsharing.booking.domain.model.valueobjects.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Booking extends AbstractAggregateRoot<Booking> {

    @Id
    @Column(length = 21)
    private String id;

    @Embedded
    private User account;

    @Embedded
    private Vehicle vehicle;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @Embedded
    private BookingDuration duration;

    @Embedded
    private BookingPrice price;

    @Embedded
    private BookingTime time;
    
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    public Booking() {
    }

    public Booking(BookingCommand command) {
        id = NanoIdUtils.randomNanoId();
        account = new User(command.getUserId(), command.getUserName());
        vehicle = new Vehicle(command.getVehicleId(), command.getVehicleName(), command.getVehiclePrice());
        status = BookingStatus.BOOKED;
        duration = new BookingDuration(command.getBookingFrom(), command.getBookingTo());
        time = new BookingTime(command.getBookingTime());
        price = new BookingPrice(vehicle.getPricing().multiply(BigDecimal.valueOf(duration.getHours())));
        paymentStatus = PaymentStatus.WAIT;
        registerEvent(new CarBookedEvent(this));
    }

    public void receive() {
        status = BookingStatus.RECEIVED;
        registerEvent(new CarReceivedEvent(this));
    }

    public void complete() {
        status = BookingStatus.COMPLETED;
        registerEvent(new BookingCompletedEvent(this));
    }
    
    public void payComplete() {
    	paymentStatus = PaymentStatus.COMPLETED;
    	registerEvent(new PaymentCompleteEvent(this));
    }
    
}
