package com.microservices.ratereview.rabbitmq;

import java.io.IOException;

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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
public class RabbitMqReceiver {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMqReceiver.class);
    @Autowired
    private RateReviewService rateReviewService;
    @Autowired
    private RabbitMQSender sender;
    

    @RabbitListener(queues = {"${rabbitmq.queue.json.name}"})
    public void createLog(@Payload RabbitDTO rbDto, Channel channel,
            @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {// data input
        
        try {
            if (rbDto == null || rbDto.getAccount() == null ||
            		rbDto.getVehicle() == null || rbDto.getDuration() == null) {
            	throw new NullPointerException();
            }
            logger.info(String.format("Received INPUT -> %s", rbDto.toString()));
            HistoryRateReviewDTO dto = new HistoryRateReviewDTO();
            dto.setFlagReview(1);
            dto.setDateReview(null);
            dto.setNumRate(0);
            dto.setReviewContent(null);
            dto.setIdUser(rbDto.getAccount().getId());
            dto.setUserName(rbDto.getAccount().getName());
            dto.setIdVehicle(rbDto.getVehicle().getId());
            dto.setVehicleName(rbDto.getVehicle().getName());
            dto.setIdBooking(rbDto.getId());
            dto.setStatusBooking(rbDto.getStatus());
            dto.setBookingFrom(rbDto.getDuration().getFrom());
            dto.setBookingTo(rbDto.getDuration().getTo());
            dto.setBookingPrice(rbDto.getPrice().getPrice());
            dto.setPaymentStatus(rbDto.getPaymentStatus());
            if(rateReviewService.checkExistBooking(rbDto.getAccount().getId()) > 0) throw new Exception("Exist Booking ID: "+ rbDto.getAccount().getId());
            rateReviewService.createHistoryRateAndReview(dto);
            channel.basicAck(deliveryTag, false);
        }
        catch(Exception e) {
            logger.error("Exception: " + e);
            try {
				channel.basicAck(deliveryTag, false);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				logger.error("Exception: " + e1);
			}
            sender.sendJsonMessage(rbDto);
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
            	throw new NullPointerException();
            }
            logger.info(String.format("Received INPUT -> %s", idBooking));
            rateReviewService.updateStatusBooking(idBooking);
            channel.basicAck(deliveryTag, false);
        }
        catch(Exception e) {
            sender.sendMessage(new Mess(idBooking));
        	try {
				channel.basicAck(deliveryTag, false);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				logger.error("Exception: " + e1);
			}
        	logger.error("Exception: " + e);
            return;
        }
        logger.info("Sussess");
    }

@AllArgsConstructor
@NoArgsConstructor
@Data
class Mess {
        private String err;
    }
}