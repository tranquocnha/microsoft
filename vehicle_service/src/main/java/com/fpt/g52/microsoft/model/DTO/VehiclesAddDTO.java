package com.fpt.g52.microsoft.model.DTO;

import lombok.Data;

@Data
public class VehiclesAddDTO {
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
