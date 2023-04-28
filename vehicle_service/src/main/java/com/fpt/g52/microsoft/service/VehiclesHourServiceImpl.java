package com.fpt.g52.microsoft.service;

import com.fpt.g52.microsoft.model.DTO.VehiclesHourDTO;
import com.fpt.g52.microsoft.model.VehiclesHour;
import com.fpt.g52.microsoft.repository.VehiclesHourRepository;
import com.fpt.g52.microsoft.util.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehiclesHourServiceImpl implements VehiclesHourService {
    @Autowired
    private VehiclesHourRepository vehiclesHourRepository;

    @Override
    public String updateStatusVehiclesHour(VehiclesHourDTO vehiclesHourDTO) throws ResourceNotFoundException {
        return vehiclesHourRepository.findById(vehiclesHourDTO.getVehiclesHourId())
                .map(vehiclesHour -> {
                    vehiclesHour.setStatus(vehiclesHourDTO.getStatus());
                    vehiclesHourRepository.save(vehiclesHour);
                    return "Update Done";
                })
                .orElseThrow(() -> new ResourceNotFoundException("Vehicles Hour Id not found for this id :: " + vehiclesHourDTO.getVehiclesHourId()));
    }
}
