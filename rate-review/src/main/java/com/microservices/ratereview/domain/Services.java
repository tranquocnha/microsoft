package com.microservices.ratereview.domain;

import java.time.LocalDate;
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
			 input.put("idBooking", idBooking);
			 String url = "xx";
			 //restTemplate.getForObject(url, InforBooKingDTO.class);
			 inf.setLocation("location 1");
			 inf.setPaymentMethod("ATM");
			 inf.setPickupTime(LocalDate.now());
			 inf.setDropoffTime(LocalDate.now());
			 inf.setIdUser(10001);
			 inf.setIdBooking(10002);
			 inf.setIdVehicle(10003);		 
			 
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception: " + e.getStackTrace());
		}
		 return inf;
	 }

}
