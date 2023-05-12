package com.fpt.g52.microsoft.service;

import com.fpt.g52.microsoft.model.DTO.VehiclesAddDTO;
import com.fpt.g52.microsoft.model.DTO.VehiclesDTO;
import com.fpt.g52.microsoft.model.Vehicles;
import com.fpt.g52.microsoft.repository.VehiclesRepository;
import com.fpt.g52.microsoft.util.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehiclesServiceImpl implements VehiclesService {
    @Autowired
    private VehiclesRepository vehiclesRepository;

    @Override
    public Vehicles addVehiclesService(VehiclesAddDTO vehiclesAddDTO) {
        Vehicles vehicles = new Vehicles();

        // Set properties of the vehicle
        getSetVehicles(vehicles, vehiclesAddDTO.getBrand(), vehiclesAddDTO.getName(), vehiclesAddDTO.getDescription(), vehiclesAddDTO.getPricing(), vehiclesAddDTO.getLocation(), vehiclesAddDTO.getCapacity(), vehiclesAddDTO.getColor(), vehiclesAddDTO.getEngineType(), vehiclesAddDTO.getImage());

        // Save the vehicle to the repository
        return vehiclesRepository.save(vehicles);
    }

    @Override
    public String updateVehicles(VehiclesDTO vehiclesDTO) throws ResourceNotFoundException {
        return vehiclesRepository.findById(vehiclesDTO.getVehicleId())
                .map(vehicles -> {
                    getSetVehicles(vehicles, vehiclesDTO.getBrand(), vehiclesDTO.getName(), vehiclesDTO.getDescription(), vehiclesDTO.getPricing(), vehiclesDTO.getLocation(), vehiclesDTO.getCapacity(), vehiclesDTO.getColor(), vehiclesDTO.getEngineType(), vehiclesDTO.getImage());
                    vehiclesRepository.save(vehicles);
                    return "Update Done ";
                })
                .orElseThrow(() -> new ResourceNotFoundException("Vehicles Id not found for this id :: " + vehiclesDTO.getVehicleId()));
    }

    public void getSetVehicles(Vehicles vehicles, String brand, String name, String description, Double pricing, String location, Integer capacity, String color, String engineType, String image) {
        if (brand != null && !brand.isEmpty()) {
            vehicles.setBrand(brand);
        }
        if (name != null && !name.isEmpty()) {
            vehicles.setName(name);
        }
        if (description != null && !description.isEmpty()) {
            vehicles.setDescription(description);
        }
        if (pricing != null) {
            vehicles.setPricing(pricing);
        }
        if (location != null && !location.isEmpty()) {
            vehicles.setLocation(location);
        }
        if (capacity != null) {
            vehicles.setCapacity(capacity);
        }
        if (color != null && !color.isEmpty()) {
            vehicles.setColor(color);
        }
        if (engineType != null && !engineType.isEmpty()) {
            vehicles.setEngineType(engineType);
        }
        if (image != null && !image.isEmpty()) {
            vehicles.setImage(image);
        }
    }
}
