package com.fpt.g52.carsharing.booking.interfaces.rest.dto.request;

import jakarta.validation.constraints.NotNull;

public record BookingRequest(

        @NotNull
        String userId,

        @NotNull
        String vehicleId,

        @NotNull
        Long from,

        @NotNull
        Long to
) {
}
