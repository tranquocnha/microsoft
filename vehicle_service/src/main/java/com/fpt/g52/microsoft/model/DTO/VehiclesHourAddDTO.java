package com.fpt.g52.microsoft.model.DTO;

import com.fpt.g52.microsoft.model.Hour;
import com.fpt.g52.microsoft.model.Vehicles;
import lombok.Data;

@Data
public class VehiclesHourAddDTO {
    private int vehiclesHourId;
    private int hourId;
    private int vehicleId;
    private Boolean status;

}
