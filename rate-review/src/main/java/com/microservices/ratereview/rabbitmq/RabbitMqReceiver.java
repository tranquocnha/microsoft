package com.microservices.ratereview.rabbitmq;

import java.sql.Date;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.microservices.ratereview.auth.Auth;
import com.microservices.ratereview.domain.InforBooKingDTO;
import com.microservices.ratereview.domain.Services;
import com.microservices.ratereview.dto.HistoryRateReviewDTO;
import com.microservices.ratereview.service.RateReviewService;
import com.rabbitmq.client.Channel;

@Component
public class RabbitMqReceiver {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMqReceiver.class);
    @Autowired
    RateReviewService rateReviewService;
    @Autowired
    private Services services;
    @Autowired
    private Auth auth;
    

    @RabbitListener(queues = {"${rabbitmq.queue.json.name}"})
    public void receivedMessage(@Payload RabbitDTO rbDto, Channel channel,
            @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {// data input
        
        try {
        	//String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQGZwdC5jb20iLCJleHAiOjE2ODQ2MDI3ODEsImlhdCI6MTY4NDU2Njc4MSwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfVVNFUiJ9XX0.ZkTpYp-ysVOkxqlBUQ8PrnHRRa2-7AtXCljvdNcyw1U";
            System.out.println("idd: " + deliveryTag);
            if (rbDto == null) {
            	System.out.println("rbDto is null");
            	channel.basicAck(deliveryTag, false);
            	return;
            }
            logger.info(String.format("Received JSON message -> %s", rbDto.toString()));
            if (!"true".equals(auth.checkToken(rbDto.getToken()))) {
            	System.out.println("Login fail");
            	channel.basicAck(deliveryTag, false);
            	return;
            }
            InforBooKingDTO info = services.getInforBooking(rbDto.getBookingId());
            HistoryRateReviewDTO dto = new HistoryRateReviewDTO();
            dto.setUserId(info.getUserId());
            dto.setBookingId(info.getBookingId());
            dto.setVehicleId(info.getVehicleId());
            dto.setFlagReview(1);
            dto.setDateLog(Date.valueOf(LocalDate.now()));
            dto.setLocation(info.getLocation());
            dto.setPaymentMethod(info.getPaymentMethod());
            dto.setPickupTime(info.getPickupTime());
            dto.setDropoffTime(info.getDropoffTime());
            rateReviewService.createHistoryRateAndReview(dto);
            channel.basicAck(deliveryTag, false);
        }
        catch(Exception e) {
            logger.error("Exception: " + e.getStackTrace());
            return;
        }
        logger.info("Sussess");
    }
}