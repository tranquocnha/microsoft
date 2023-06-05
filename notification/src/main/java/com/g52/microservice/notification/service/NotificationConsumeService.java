package com.g52.microservice.notification.service;

import com.fpt.g52.common_service.notification.model.shared.payload.rabbitmq.OrderPayload;
import com.fpt.g52.common_service.notification.model.shared.payload.ws.OrderMessagePayload;
import com.g52.microservice.notification.constant.NotificationConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = NotificationConstant.NOTIFY_ORDER_PAYMENT_TOPIC_QUEUE)
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumeService {

  private final MessageService messageService;
  private final SimpMessagingTemplate simpMessagingTemplate;


  @RabbitHandler
  public void consumeMessage(final OrderPayload payload) {
    log.info("consumeMessage(...) starting with payload = {}", payload);

    OrderMessagePayload message = new OrderMessagePayload()
        .setHandled(false)
        .setPrice(payload.getPrice())
        .setBookingId(payload.getBookingId());

    switch (payload.getSender()) {
      case BOOKING_SERVICE -> messageService.createMessage(payload);

      case PAYMENT_SERVICE -> {
        message.setHandled(true);
        messageService.updateMessage(payload);
      }

      case UNKNOWN -> throw new RuntimeException("Could not found sender name!!!");
    }

    log.info("send message into receiver = {} with message payload = {}", payload.getReceiver(), message);
    simpMessagingTemplate.convertAndSendToUser(payload.getReceiver(), "/private", message);
  }
}
