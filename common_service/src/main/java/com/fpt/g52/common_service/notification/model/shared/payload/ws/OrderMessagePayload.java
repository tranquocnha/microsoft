package com.fpt.g52.common_service.notification.model.shared.payload.ws;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
@ToString
public class OrderMessagePayload implements Serializable {

  private static final long serialVersionUID = 1L;

  private boolean handled;
  private String bookingId;
  private BigDecimal price;
}
