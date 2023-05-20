package com.microservices.ratereview.domain;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class Services {
	private static final Logger logger = LoggerFactory.getLogger(Services.class);
	 public InforBooKingDTO getInforBooking(String idBooking) {
		 RestTemplate restTemplate = new RestTemplate();
		 InforBooKingDTO inf = new InforBooKingDTO();
		 try {
			 Map<String, String> input = new HashMap<String, String>();
			 input.put("bookingId", idBooking);
			 String url = "xx";
			 inf = restTemplate.getForObject(url, InforBooKingDTO.class);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception: " + e.getStackTrace());
		}
		 return inf;
	 }

}
