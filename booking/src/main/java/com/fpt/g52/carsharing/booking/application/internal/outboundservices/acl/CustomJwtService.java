package com.fpt.g52.carsharing.booking.application.internal.outboundservices.acl;

import org.springframework.stereotype.Component;

import com.fpt.g52.common_service.util.JwtService;

@Component
public class CustomJwtService extends JwtService{
	
	public CustomJwtService() throws Exception {
		super("certs/public.pem");
	}
}
