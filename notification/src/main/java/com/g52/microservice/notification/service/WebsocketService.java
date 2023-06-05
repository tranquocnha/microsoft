package com.g52.microservice.notification.service;

import com.fpt.g52.common_service.notification.model.shared.payload.ws.GetMessagePayload;
import com.fpt.g52.common_service.notification.model.shared.payload.ws.OrderMessagePayload;
import com.g52.microservice.notification.model.entity.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebsocketService {

  private final MessageService messageService;
  private final SimpMessagingTemplate simpMessagingTemplate;

  public void getMessageAndSend(final GetMessagePayload payload) {
    if (payload == null || payload.getUsername() == null || payload.getMessageType() == null) {
      log.warn("payload is invalid !!!");
      throw new RuntimeException("payload is invalid !!!");
    }

    log.info("getMessageAndSend stating with payload = {}", payload);

    switch (payload.getMessageType()) {
      case ORDER -> {
        List<OrderMessagePayload> orderMessagePayloads = this.listOrderMessagePayload(payload.getUsername());
        simpMessagingTemplate.convertAndSendToUser(payload.getUsername(), "/private", orderMessagePayloads);
      }
      case MAIL -> {
        //todo something for mail
      }
      case UNKNOWN -> {
        log.warn("Could not found message type!!!");
        throw new RuntimeException("Could not found message type!!!");
      }
    }
  }

  private List<OrderMessagePayload> listOrderMessagePayload(String username) {
    List<Message> messageList = this.messageService.listMessageByReceiver(username);

    return messageList.stream().filter(m -> !m.isConsumed())
        .map(m -> new OrderMessagePayload()
            .setBookingId(m.getBookingId())
            .setPrice(m.getPrice())
            .setHandled(false)).collect(Collectors.toList());
  }
}
