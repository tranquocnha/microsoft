package com.fpt.g52.microsoft.model.DTO;

import com.fpt.g52.microsoft.model.Hour;
import com.fpt.g52.microsoft.model.Vehicles;
import lombok.Data;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
public class VehiclesHourDTO {
    private int vehiclesHourId ;
    private Boolean status;
}
