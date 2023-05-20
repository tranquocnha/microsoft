package com.fpt.g52.carsharing.booking.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingDuration {

    @Column(name = "booking_from")
    private Long from;

    @Column(name = "booking_to")
    private Long to;

    public Long getHours() {
        return (to - from) / (1000 * 60 * 60);
    }
}
