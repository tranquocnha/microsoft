package com.fpt.g52.microsoft.controller;

import com.fpt.g52.common_service.dto.vehicle.VehiclesHourDTO;
import com.fpt.g52.microsoft.model.DTO.VehiclesAddDTO;
import com.fpt.g52.microsoft.model.DTO.VehiclesHourAddDTO;
import com.fpt.g52.microsoft.model.Vehicles;
import com.fpt.g52.microsoft.model.VehiclesHour;
import com.fpt.g52.microsoft.repository.VehiclesHourRepository;
import com.fpt.g52.microsoft.service.VehiclesHourService;
import com.fpt.g52.microsoft.util.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/api/vehicles/hour")
public class VehiclesHourController {
    @Autowired
    private VehiclesHourRepository vehiclesHourRepository;
    @Autowired
    private VehiclesHourService vehiclesHourService;

    @GetMapping("/")
    public List<VehiclesHour> getAllVehiclesHour() {
        return vehiclesHourRepository.findAll();
    }

    @GetMapping("/{vehiclesHourId}")
    public ResponseEntity<VehiclesHour> getVehiclesHourById(@PathVariable(value = "vehiclesHourId") int vehiclesHourId)
            throws ResourceNotFoundException {
        VehiclesHour vehiclesHour = vehiclesHourRepository.findById(vehiclesHourId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicles Hour Id not found for this id :: " + vehiclesHourId));
        return ResponseEntity.ok().body(vehiclesHour);
    }

    @GetMapping("/status/{status}")
    public List<VehiclesHour> getAllVehiclesHourUI(@PathVariable(value = "status") String status) {
        try {
            boolean parsedStatus = Boolean.parseBoolean(status);
            List<VehiclesHour> vehiclesHour = vehiclesHourRepository.findByStatus(parsedStatus);
            return ResponseEntity.ok().body(vehiclesHour).getBody();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @GetMapping("/search")
    public ResponseEntity<List<VehiclesHour>> searchVehiclesHour(@RequestParam(value = "name", required = false) String name,
                                                                 @RequestParam(value = "brand", required = false) String brand,
                                                                 @RequestParam(value = "pricing", required = false) Double pricing,
                                                                 @RequestParam(value = "location", required = false) String location,
                                                                 @RequestParam(value = "engineType", required = false) String engineType) {
        List<VehiclesHour> vehiclesHourList = vehiclesHourService.searchProducts(name, brand, pricing, location, engineType);

        return ResponseEntity.ok().body(vehiclesHourList);
    }

    @PutMapping("/")
    public ResponseEntity<String> updateVehiclesHourStatus(@RequestBody VehiclesHourDTO vehiclesHourDTO) {
        try {
            String messegeUpdate = vehiclesHourService.updateStatusVehiclesHour(vehiclesHourDTO);
            return new ResponseEntity<>(messegeUpdate, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity<VehiclesHour> addVehicles(@RequestBody VehiclesHourAddDTO vehiclesHourAddDTO) {
        try {
            VehiclesHour savedVehicle = vehiclesHourService.addVehiclesHourService(vehiclesHourAddDTO);
            // Return a response with status 201 and the saved vehicle
            return ResponseEntity.status(HttpStatus.CREATED).body(savedVehicle);
        } catch (Exception e) {
            // If an error occurs, return a response with status 400 and an error message
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


}
