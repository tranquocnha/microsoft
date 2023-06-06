package com.g52.microservice.notification.config;

import com.fpt.g52.common_service.util.JwtService;
import org.springframework.stereotype.Component;

@Component
public class JwtServiceCustom extends JwtService {

  public JwtServiceCustom() throws Exception {
    super("certs/public.pem");
  }
}
