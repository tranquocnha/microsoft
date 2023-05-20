package com.microservices.ratereview.auth;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Auth {

	private static final Logger logger = LoggerFactory.getLogger(Auth.class);
    @Value("${uri.identity}")
    private String uriIdtt;

	public String checkToken(String token) {
		RestTemplate restTemplate = new RestTemplate();
		String result;
		try {

			Map<String, String> input = new HashMap<String, String>();
			input.put("token", token);
			String uri = uriIdtt + "/api/v1/auth/v1/validate-token?token={token}";
			ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class, input);
			result = responseEntity.getBody();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception: " + e.getStackTrace());
			return "false";
		}
		return result;
	}
}
