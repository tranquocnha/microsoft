package com.fpt.g52.carsharing.booking.application.internal.outboundservices.acl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fpt.g52.carsharing.booking.domain.model.entities.User;

@Service
public class UserService {

    @Autowired
    private Environment env;
    
    public User getUserByid(String token) {
        if (!checkTokenExp(token)) {
            return null;
        }
//        User user = new  RestTemplate().getForObject(env.getProperty("service.identity.local.domain")+ env.getProperty("service.identity.local.url") + token, User.class);
        User user = new  User("admin", "admin");
        return user;
    }
    
    private boolean checkTokenExp(String token) {
        return true;
    }
}
