package com.g52.microservice.notification.controller;


import com.fpt.g52.common_service.notification.model.shared.constant.NotificationSharedConstant;
import com.fpt.g52.common_service.notification.model.shared.payload.rabbitmq.OrderPayload;
import com.g52.microservice.notification.config.JwtServiceCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notify/mocks")
@Slf4j
@RequiredArgsConstructor
public class MockController {

  private final RabbitTemplate rabbitTemplate;
  private final JwtServiceCustom jwtServiceCustom;

  @PostMapping("/create-order")
  public void createOrderPayment(@RequestBody OrderPayload payload, @RequestHeader("Authorization") String token) {
    log.info("createOrderPayment with payload = {}", payload);
    log.info("createOrderPayment with token = {}", token);
    boolean isValid = jwtServiceCustom.validateToken(token);
    log.info("isValid = {}", isValid);

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
