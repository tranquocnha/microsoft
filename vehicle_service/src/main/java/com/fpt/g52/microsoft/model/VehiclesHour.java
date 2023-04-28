package com.fpt.g52.microsoft.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "vehicles_hour")
@Data
public class VehiclesHour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int vehiclesHourId ;

    @ManyToOne(targetEntity = Hour.class)
    @JoinColumn(name = "hour_id")
    private Hour hour;

    @ManyToOne(targetEntity = Vehicles.class)
    @JoinColumn(name = "vehicle_id")
    private Vehicles vehicle;

    private Boolean status;


}
