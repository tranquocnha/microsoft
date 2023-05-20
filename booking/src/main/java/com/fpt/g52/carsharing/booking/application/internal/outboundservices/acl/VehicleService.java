package com.fpt.g52.carsharing.booking.application.internal.outboundservices.acl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fpt.g52.carsharing.booking.domain.model.entities.Vehicle;

@Service
public class VehicleService {
    
    @Autowired
    private Environment env;
    
    public Vehicle getVehicleById(String id) throws Exception{
        
        return new RestTemplate().getForObject(env.getProperty("service.vehicle.local.domain")+ env.getProperty("service.vehicle.local.url") + id, Vehicle.class);
    }
}
