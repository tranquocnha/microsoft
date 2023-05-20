package com.fpt.g52.carsharing.booking.domain.model.commands;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookingCommand {

    private String userId;

    private String userName;

    private String vehicleId;

    private String vehicleName;

    private BigDecimal vehiclePrice;

    private Long bookingFrom;

    private Long bookingTo;

    private Long bookingTime;
}
