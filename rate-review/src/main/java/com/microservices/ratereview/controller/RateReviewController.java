package com.microservices.ratereview.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.ratereview.dto.HistoryRateReviewDTO;
import com.microservices.ratereview.rabbitmq.RabbitMqSender;
import com.microservices.ratereview.service.RateReviewService;

@RestController
@RequestMapping(value = "rate-review")
public class RateReviewController {

    private Logger logger = LoggerFactory.getLogger(RateReviewController.class);
    @Autowired
    private Environment environment;
    @Autowired
    private RabbitMqSender rabbitMqSender;
    @Autowired
    private RateReviewService rateReviewService;

    @GetMapping("/get")
    public String helloWorld() {
        String port = environment.getProperty("local.server.port");
        String host = environment.getProperty("HOSTNAME");
        String version = "v0";

        logger.info(port + " " + version + " " + host);
        return "Version " + version + " Hello rate-review " + host;
    }

    // Get all
    @GetMapping("/historyratereview")
    public List<HistoryRateReviewDTO> getAllEmployees() {
        return rateReviewService.getHistoryRateReview();
    }

    // Test rabbit
    @PostMapping(value = "/sendmapping")
    public String publishUserDetails(@RequestBody String messafe) {
        rabbitMqSender.sendMessage(messafe);
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
}
