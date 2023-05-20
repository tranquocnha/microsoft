package com.microservices.ratereview.auth;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Auth {
	
	private static final Logger logger = LoggerFactory.getLogger(Auth.class);
	public boolean checkToken(String token) {
		RestTemplate restTemplate = new RestTemplate();
		 Boolean ss = true;
		 try {
			 
			 Map<String, String> input = new HashMap<String, String>();
			 input.put("token", token);
			 String url = "xx";
			 ss = restTemplate.getForObject(url, Boolean.class);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception: " + e.getStackTrace());
		}
		 return ss;
	}
}
