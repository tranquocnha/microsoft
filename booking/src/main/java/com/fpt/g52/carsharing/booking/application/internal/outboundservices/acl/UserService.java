package com.fpt.g52.carsharing.booking.application.internal.outboundservices.acl;

import org.springframework.stereotype.Service;

import com.fpt.g52.carsharing.booking.domain.model.entities.User;

@Service
public class UserService {
	
	private final CustomJwtService customJwtService;
	
	public UserService(CustomJwtService customJwtService) {
		this.customJwtService = customJwtService;
	}

	public User getUserByid(String token) {
    	
        
        if(!customJwtService.validateToken(token)) {
        	return null;
        }
        
        String username = customJwtService.extractUsername(token);
        
       return new User(username, username);
    }
    
}
