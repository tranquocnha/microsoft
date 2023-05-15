package com.fpt.g52.microsoft.service;

import com.fpt.g52.common_service.dto.vehicle.VehiclesHourDTO;
import com.fpt.g52.microsoft.model.DTO.VehiclesHourAddDTO;
import com.fpt.g52.microsoft.model.Hour;
import com.fpt.g52.microsoft.model.Vehicles;
import com.fpt.g52.microsoft.model.VehiclesHour;
import com.fpt.g52.microsoft.repository.HourRepository;
import com.fpt.g52.microsoft.repository.VehiclesHourRepository;
import com.fpt.g52.microsoft.repository.VehiclesRepository;
import com.fpt.g52.microsoft.util.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehiclesHourServiceImpl implements VehiclesHourService {
    @Autowired
    private VehiclesHourRepository vehiclesHourRepository;
    @Autowired
    private VehiclesRepository vehiclesRepository;
    @Autowired
    private HourRepository hourRepository;

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

    @Override
    public List<VehiclesHour> searchProducts(String name, String brand, Double pricing, String location, String engineType) {
        List<VehiclesHour> allVehiclesHour = vehiclesHourRepository.findAll();

        // Lọc sản phẩm theo tên (nếu có)
        if (name != null && !name.isEmpty()) {
            allVehiclesHour = allVehiclesHour.stream()
                    .filter(vehicle -> vehicle.getVehicle().getName().toLowerCase().contains(name.toLowerCase()))
                    .collect(Collectors.toList());
        }

        // Lọc sản phẩm theo giá (nếu có)
        if (pricing != null) {
            allVehiclesHour = allVehiclesHour.stream()
                    .filter(vehicle -> vehicle.getVehicle().getPricing() <= pricing)
                    .collect(Collectors.toList());
        }

        // Lọc sản phẩm theo thể loại (nếu có)
        if (brand != null && !brand.isEmpty()) {
            allVehiclesHour = allVehiclesHour.stream()
                    .filter(vehicle -> vehicle.getVehicle().getBrand().equalsIgnoreCase(brand))
                    .collect(Collectors.toList());
        }

        if (location != null && !location.isEmpty()) {
            allVehiclesHour = allVehiclesHour.stream()
                    .filter(vehicle -> vehicle.getVehicle().getLocation().equalsIgnoreCase(location))
                    .collect(Collectors.toList());
        }

        if (engineType != null && !engineType.isEmpty()) {
            allVehiclesHour = allVehiclesHour.stream()
                    .filter(vehicle -> vehicle.getVehicle().getEngineType().equalsIgnoreCase(engineType))
                    .collect(Collectors.toList());
        }

        return allVehiclesHour;
    }

    @Override
    public VehiclesHour addVehiclesHourService(VehiclesHourAddDTO vehiclesHourAddDTO) {
        if (vehiclesHourAddDTO == null) {
            throw new IllegalArgumentException("Null vehiclesHourAddDTO parameter");
        }

        VehiclesHour vehiclesHour = new VehiclesHour();
        Vehicles vehicles = vehiclesRepository.findById(vehiclesHourAddDTO.getVehicleId()).orElse(null);
        Hour hour = hourRepository.findById(vehiclesHourAddDTO.getHourId()).orElse(null);

        if (hour == null) {
            throw new IllegalArgumentException("Invalid hour ID: " + vehiclesHourAddDTO.getHourId());
        }

        if (vehicles == null) {
            throw new IllegalArgumentException("Invalid vehicle ID: " + vehiclesHourAddDTO.getVehicleId());
        }

        vehiclesHour.setHour(hour);
        vehiclesHour.setVehicle(vehicles);
        vehiclesHour.setStatus(vehiclesHourAddDTO.getStatus());

        return vehiclesHourRepository.save(vehiclesHour);
    }
}
