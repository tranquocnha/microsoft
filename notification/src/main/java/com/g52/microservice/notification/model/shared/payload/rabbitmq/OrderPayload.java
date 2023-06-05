package com.g52.microservice.notification.model.shared.payload.rabbitmq;

import com.g52.microservice.notification.model.shared.enums.PaymentStatus;
import com.g52.microservice.notification.model.shared.enums.Sender;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
@ToString
public class OrderPayload implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  private Sender sender;
  private String receiver;
  private String bookingId;
  private BigDecimal price;
  private PaymentStatus paymentStatus;
}
