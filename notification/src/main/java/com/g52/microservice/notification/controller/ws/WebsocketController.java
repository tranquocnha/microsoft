package com.g52.microservice.notification.controller.ws;

import com.fpt.g52.common_service.notification.model.shared.payload.ws.GetMessagePayload;
import com.g52.microservice.notification.service.WebsocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebsocketController {

  private final WebsocketService websocketService;

  @MessageMapping("/get-message")
  public void recMessage(@Payload GetMessagePayload message) {
    this.websocketService.getMessageAndSend(message);
  }
}
