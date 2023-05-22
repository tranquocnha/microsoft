package com.fpt.g52.carsharing.booking.application.internal.outboundservices.acl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestTemplate;

import com.fpt.g52.carsharing.booking.domain.exceptions.ResourceInvalidException;
import com.fpt.g52.carsharing.booking.domain.model.entities.User;

@Service
public class UserService {

    @Autowired
    private Environment env;
    
    public User getUserByid(String token) {
        String username;
        try {
            username = new  RestTemplate().getForObject(env.getProperty("service.identity.local.domain")+ env.getProperty("service.identity.local.url") + token, String.class);
        } catch (RestClientException e) {
            return null;
        }
        if (username == null) {
            return null;
        }
        
       return new  User(username, username);
    }
    
    private boolean checkTokenExp(String token) {
        return true;
    }
}
