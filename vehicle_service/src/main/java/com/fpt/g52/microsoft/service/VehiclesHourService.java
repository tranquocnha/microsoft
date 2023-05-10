package com.fpt.g52.microsoft.service;

import com.fpt.g52.common_service.dto.vehicle.VehiclesHourDTO;
import com.fpt.g52.microsoft.util.ResourceNotFoundException;

public interface VehiclesHourService {
    String updateStatusVehiclesHour(VehiclesHourDTO vehiclesHourDTO) throws ResourceNotFoundException;
}
