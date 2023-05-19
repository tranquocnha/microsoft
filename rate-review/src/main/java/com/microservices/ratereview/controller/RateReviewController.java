package com.microservices.ratereview.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.ratereview.domain.InforBooKingDTO;
import com.microservices.ratereview.domain.Services;
import com.microservices.ratereview.dto.HistoryRateReviewDTO;
import com.microservices.ratereview.entity.HistoryRateReviewEntity;
import com.microservices.ratereview.rabbitmq.RabbitMqSender;
import com.microservices.ratereview.service.RateReviewService;

@RestController
@RequestMapping(value = "rate-review")
@CrossOrigin(origins="*")
public class RateReviewController {

//    private Logger logger = LoggerFactory.getLogger(RateReviewController.class);
    //@Autowired
    //private Environment environment;
    @Autowired
    private RabbitMqSender rabbitMqSender;
    @Autowired
    private RateReviewService rateReviewService;
    @Autowired
    private Services services;
    @GetMapping("/getinforbk")
    public InforBooKingDTO helloWorld(@RequestParam String idBooking) {
        return services.getInforBooking(idBooking);
    }

    // Get all
    @GetMapping("/historyratereview")
    public List<HistoryRateReviewDTO> getAllEmployees() {
        return rateReviewService.getHistoryRateReview();
    }

    // Test rabbit
    @PostMapping(value = "/testmq")
    public String publishUserDetails(@RequestBody String idBooking) {
        rabbitMqSender.sendMessage(idBooking);
        return "OK";
    }

    // Avg rate for vehicle
    @GetMapping("/ratenumvehicle")
    public int getRateMumVehicle(@RequestParam int idVehicle) {
        return rateReviewService.avgRateNumVehicle(idVehicle);
    }

    // Get review by id Vehicle
    @GetMapping("/reviewvehicle")
    public List<HistoryRateReviewDTO> getReviewVehicle(@RequestParam int idVehicle) {
        return rateReviewService.getReviewVehicle(idVehicle);
    }
    
    //Create review
    @PutMapping("/createreview/{idLog}")
    public HistoryRateReviewEntity createReivew(@PathVariable int idLog ,@RequestBody HistoryRateReviewDTO historyRateReviewDTO){
    	System.out.println(historyRateReviewDTO);
    	return rateReviewService.createReview(idLog, historyRateReviewDTO);
    }
}
