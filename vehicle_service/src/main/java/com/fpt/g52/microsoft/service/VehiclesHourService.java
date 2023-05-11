package com.fpt.g52.microsoft.service;

import com.fpt.g52.common_service.dto.vehicle.VehiclesHourDTO;
import com.fpt.g52.microsoft.model.VehiclesHour;
import com.fpt.g52.microsoft.util.ResourceNotFoundException;

import java.util.List;

public interface VehiclesHourService {
    String updateStatusVehiclesHour(VehiclesHourDTO vehiclesHourDTO) throws ResourceNotFoundException;

    List<VehiclesHour> searchProducts(String name, String brand, Double pricing, String location, String engineType);
}
