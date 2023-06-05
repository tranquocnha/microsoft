package com.g52.microservice.notification.model.entity;

import com.g52.microservice.notification.model.enums.MessageType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "message")
@Entity
@Getter
@Setter
@Accessors(chain = true)
@ToString
public class Message implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;

  private String sender;

  private String receiver;

  private String bookingId;

  private String payload;

  @Enumerated(EnumType.STRING)
  private MessageType messageType;

  private boolean consumed;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private Long createdBy;

  private Long updatedBy;
}
