package com.fpt.g52.microsoft.model.DTO;

import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class VehiclesDTO {
    private int vehicleId;
    private String name;
    private String description;
    private String brand;
    private Double pricing;
    private String location;
    private String color;
    private Integer capacity;
    private String engineType;
    private String image;
}
