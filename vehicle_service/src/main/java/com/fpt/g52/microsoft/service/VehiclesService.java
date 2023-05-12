package com.fpt.g52.microsoft.service;

import com.fpt.g52.microsoft.model.DTO.VehiclesAddDTO;
import com.fpt.g52.microsoft.model.DTO.VehiclesDTO;
import com.fpt.g52.microsoft.model.Vehicles;
import com.fpt.g52.microsoft.util.ResourceNotFoundException;

public interface VehiclesService {
    Vehicles addVehiclesService(VehiclesAddDTO vehiclesAddDTO);


    String updateVehicles(VehiclesDTO vehiclesDTO) throws ResourceNotFoundException;
}
