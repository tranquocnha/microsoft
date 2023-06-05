package com.g52.microservice.notification.controller;

import com.g52.microservice.notification.model.shared.constant.NotificationSharedConstant;
import com.g52.microservice.notification.model.shared.payload.rabbitmq.OrderPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notify/mocks")
@Slf4j
@RequiredArgsConstructor
public class MockController {

  private final RabbitTemplate rabbitTemplate;


  @PostMapping("/create-order")
  public void createOrderPayment(@RequestBody OrderPayload payload) {
    log.info("createOrderPayment with payload = {}", payload);
    rabbitTemplate.convertAndSend(NotificationSharedConstant.NOTIFY_TOPIC_EXCHANGE,
        NotificationSharedConstant.NOTIFY_ORDER_PAYMENT_ROUTING_KEY,
        payload);
  }

  @PostMapping("/handle-order")
  public void handleOrderPayment(@RequestBody OrderPayload payload) {
    log.info("handleOrderPayment with payload = {}", payload);
    rabbitTemplate.convertAndSend(NotificationSharedConstant.NOTIFY_TOPIC_EXCHANGE,
        NotificationSharedConstant.NOTIFY_ORDER_PAYMENT_ROUTING_KEY,
        payload);
  }

}
