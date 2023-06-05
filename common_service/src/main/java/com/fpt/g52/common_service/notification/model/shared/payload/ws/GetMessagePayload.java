package com.fpt.g52.common_service.notification.model.shared.payload.ws;

import com.fpt.g52.common_service.notification.model.shared.enums.MessageType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@Setter
@Accessors(chain = true)
@ToString
public class GetMessagePayload implements Serializable {

  private static final long serialVersionUID = 1L;

  private String username;
  private MessageType messageType;
}
