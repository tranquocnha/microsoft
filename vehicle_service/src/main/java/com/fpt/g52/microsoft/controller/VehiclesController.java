package com.fpt.g52.microsoft.controller;

import com.fpt.g52.microsoft.model.DTO.VehiclesAddDTO;
import com.fpt.g52.microsoft.model.DTO.VehiclesDTO;
import com.fpt.g52.microsoft.model.Vehicles;
import com.fpt.g52.microsoft.model.VehiclesHour;
import com.fpt.g52.microsoft.repository.VehiclesRepository;
import com.fpt.g52.microsoft.service.VehiclesService;
import com.fpt.g52.microsoft.util.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/api/vehicles")
public class VehiclesController {
    @Autowired
    private VehiclesRepository vehiclesRepository;
    @Autowired
    private VehiclesService vehiclesService;

    @GetMapping("/")
    public List<Vehicles> getAllVehicles() {
        return vehiclesRepository.findAll();
    }

    @GetMapping("/{vehiclesId}")
    public ResponseEntity<Vehicles> getVehiclesById(@PathVariable(value = "vehiclesId") int vehiclesId)
            throws ResourceNotFoundException {
        Vehicles vehicles = vehiclesRepository.findById(vehiclesId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicles Id not found for this id :: " + vehiclesId));
        return ResponseEntity.ok().body(vehicles);
    }

    @PutMapping("/")
    public ResponseEntity<String> updateVehiclesHourStatus(@RequestBody VehiclesDTO vehiclesDTO) {
        try {
            String messegeUpdate = vehiclesService.updateVehicles(vehiclesDTO);
            return new ResponseEntity<>(messegeUpdate, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/")
    public ResponseEntity<String> deleteVehicles(@RequestBody int vehiclesId) throws ResourceNotFoundException {
        Vehicles vehicle = vehiclesRepository.findById(vehiclesId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));
        vehiclesRepository.delete(vehicle);
        return new ResponseEntity<>("Vehicle deleted successfully", HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Vehicles> addVehicles(@RequestBody VehiclesAddDTO vehiclesAddDTO) {
        try {
            Vehicles savedVehicle = vehiclesService.addVehiclesService(vehiclesAddDTO);
            // Return a response with status 201 and the saved vehicle
            return ResponseEntity.status(HttpStatus.CREATED).body(savedVehicle);
        } catch (Exception e) {
            // If an error occurs, return a response with status 400 and an error message
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Vehicles>> searchVehicles(@RequestParam(value = "name", required = false) String name,
                                                         @RequestParam(value = "brand", required = false) String brand,
                                                         @RequestParam(value = "pricing", required = false) Double pricing,
                                                         @RequestParam(value = "location", required = false) String location,
                                                         @RequestParam(value = "engineType", required = false) String engineType) {
        List<Vehicles> vehiclesList = vehiclesService.searchProducts(name, brand, pricing, location, engineType);

        return ResponseEntity.ok().body(vehiclesList);
    }

}
