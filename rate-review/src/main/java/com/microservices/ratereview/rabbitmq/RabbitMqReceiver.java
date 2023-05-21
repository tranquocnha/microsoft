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
import com.microservices.ratereview.exception.ResourceException;
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
    public void createLog(@Payload RabbitDTO rbDto, Channel channel,
            @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {// data input
        
        try {
            if (rbDto == null) {
            	channel.basicAck(deliveryTag, false);
            	throw new ResourceException("rbDto is null");
            }
            logger.info(String.format("Received INPUT -> %s", rbDto.toString()));
//            if (!"true".equals(auth.checkToken(rbDto.getToken()))) {
//            	System.out.println("Login fail");
//            	channel.basicAck(deliveryTag, false);
//            	return;
//            }
            HistoryRateReviewDTO dto = new HistoryRateReviewDTO();
            dto.setFlagReview(0);
            dto.setDateReview(null);
            dto.setNumRate(0);
            dto.setReviewContent(null);
            dto.setIdUser(rbDto.getUser().getId());
            dto.setUserName(rbDto.getUser().getName());
            dto.setIdVehicle(rbDto.getVehicle().getId());
            dto.setVehicleName(rbDto.getVehicle().getName());
            dto.setIdBooking(rbDto.getId());
            dto.setStatusBooking(rbDto.getStatus());
            dto.setBookingFrom(rbDto.getDuration().getFrom());
            dto.setBookingTo(rbDto.getDuration().getTo());
            dto.setBookingPrice(rbDto.getPrice().getPrice());
            dto.setPaymentStatus(rbDto.getPaymentStatus());
            if(rateReviewService.checkExistBooking(rbDto.getUser().getId()) > 0) throw new ResourceException("Exist Booking ID: "+ rbDto.getUser().getId());
            rateReviewService.createHistoryRateAndReview(dto);
            channel.basicAck(deliveryTag, false);
        }
        catch(Exception e) {
            logger.error("Exception: " + e.getStackTrace());
            return;
        }
        logger.info("Sussess");
    }
    
    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void updateStatusBooking(@Payload String idBooking, Channel channel,
            @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {// data input
        
        try {
            System.out.println("idd: " + deliveryTag);
            if (idBooking == null || "".equals(idBooking)) {
            	channel.basicAck(deliveryTag, false);
            	throw new ResourceException("rbDto is null");
            }
            logger.info(String.format("Received INPUT -> %s", idBooking));
            if(rateReviewService.checkExistBooking(idBooking) > 0) throw new ResourceException("Exist Booking ID: "+ idBooking);
            rateReviewService.updateStatusBooking(idBooking);
            channel.basicAck(deliveryTag, false);
        }
        catch(Exception e) {
            logger.error("Exception: " + e.getStackTrace());
            return;
        }
        logger.info("Sussess");
    }
}