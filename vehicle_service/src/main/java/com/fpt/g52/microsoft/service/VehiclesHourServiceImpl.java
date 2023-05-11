package com.fpt.g52.microsoft.service;

import com.fpt.g52.common_service.dto.vehicle.VehiclesHourDTO;
import com.fpt.g52.microsoft.model.VehiclesHour;
import com.fpt.g52.microsoft.repository.VehiclesHourRepository;
import com.fpt.g52.microsoft.util.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
}
