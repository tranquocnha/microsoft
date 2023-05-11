package com.microservices.ratereview.rabbitmq;

import java.io.IOException;
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

import com.microservices.ratereview.dto.HistoryRateReviewDTO;
import com.microservices.ratereview.service.RateReviewService;
import com.rabbitmq.client.Channel;

@Component
public class RabbitMqReceiver {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMqReceiver.class);
    @Autowired
    RateReviewService rateReviewService;

    @RabbitListener(queues = "MQ_DEMO")
    public void receivedMessage(@Payload Object message, Channel channel,
            @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {// data input
        
        try {
            System.out.println("idd: " + deliveryTag);
            logger.info("Details Received is.. " + message);
            // Token login check user
            // get infor user
            // Token login not exist logger.error("Token: "+ message.token + " not exist");
            HistoryRateReviewDTO dto = new HistoryRateReviewDTO();
            dto.setIdUser(1);
            dto.setIdBooking(2);
            dto.setIdVehicle(3);
            dto.setFlagReview(1);
            dto.setDateLog(Date.valueOf(LocalDate.now()));
            rateReviewService.createHistoryRateAndReview(dto);
            channel.basicAck(deliveryTag, false);
        }
        catch(Exception e) {
            logger.error("Exception: " + e.getStackTrace());
        }
        logger.info("Sussess");
    }
}