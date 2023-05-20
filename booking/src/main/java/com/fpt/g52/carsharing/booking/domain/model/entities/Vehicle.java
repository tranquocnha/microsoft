package com.fpt.g52.carsharing.booking.domain.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Vehicle {

    @Column(name = "vehicle_id")
    private String id;

    @Column(name = "vehicle_name")
    private String name;

    @Column(name = "vehicle_price")
    private BigDecimal pricing;
    
}
