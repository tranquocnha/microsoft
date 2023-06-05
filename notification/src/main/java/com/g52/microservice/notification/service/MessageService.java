package com.g52.microservice.notification.service;

import com.fpt.g52.common_service.notification.model.shared.payload.rabbitmq.OrderPayload;
import com.g52.microservice.notification.model.entity.Message;
import com.g52.microservice.notification.model.enums.MessageType;
import com.g52.microservice.notification.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {

  private final MessageRepository messageRepository;

  @Transactional
  public void createMessage(OrderPayload payload) {
    log.info("createMessage with payload = {}", payload);
    LocalDateTime now = LocalDateTime.now();
    Message message = new Message()
        .setMessageType(MessageType.ORDER_PAYMENT)
        .setConsumed(false)
        .setBookingId(payload.getBookingId())
        .setPayload(payload.toString())
        .setReceiver(payload.getReceiver())
        .setSender(payload.getSender().name())
        .setCreatedAt(now)
        .setUpdatedAt(now)
        .setCreatedBy(-1L)
        .setUpdatedBy(-1L);

    messageRepository.save(message);
  }

  @Transactional
  public void updateMessage(OrderPayload payload) {
    log.info("updateMessage with payload = {}", payload);
    Message existedMessage = messageRepository.findByReceiverAndBookingId(payload.getReceiver(), payload.getBookingId())
        .orElseThrow(() -> {
          log.warn("Could not found message with receiver = {} and booking id = {}", payload.getReceiver(), payload.getBookingId());
          throw new RuntimeException("Message is not existed before");
        });
    LocalDateTime now = LocalDateTime.now();
    existedMessage.setSender(payload.getSender().name());
    existedMessage.setPayload(payload.toString());
    existedMessage.setUpdatedAt(now);
    existedMessage.setConsumed(true);

    messageRepository.save(existedMessage);
  }
}
