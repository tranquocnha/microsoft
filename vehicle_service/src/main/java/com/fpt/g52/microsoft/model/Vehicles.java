package com.fpt.g52.microsoft.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "vehicles")
@Data
public class Vehicles {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int vehicleId;
    private String name;
    private String description;
    private String brand;
    private Double pricing;
    private String location;
    private String color;
    private int capacity;
    private String engineType;
    private String image;
}
