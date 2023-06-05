package com.fpt.g52.common_service.notification.model.shared.payload.rabbitmq;

import com.fpt.g52.common_service.notification.model.shared.enums.PaymentStatus;
import com.fpt.g52.common_service.notification.model.shared.enums.Sender;
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
public class OrderPayload implements Serializable {

  private static final long serialVersionUID = 1L;

  private Sender sender;
  private String receiver;
  private String bookingId;
  private BigDecimal price;
  private PaymentStatus paymentStatus;
}
