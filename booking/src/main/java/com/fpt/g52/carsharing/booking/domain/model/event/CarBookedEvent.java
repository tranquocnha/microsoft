package com.fpt.g52.carsharing.booking.domain.model.event;

import com.fpt.g52.carsharing.booking.domain.model.aggregates.Booking;

public record CarBookedEvent(Booking booking) {
}
