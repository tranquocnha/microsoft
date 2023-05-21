package com.microservices.ratereview.rabbitmq;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RabbitDTO {
    private String id;
	private User user;
	private Vehicle vehicle;
	private String status;
	private BookingDuration duration;
	private String paymentStatus;
	private BookingPrice price;
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class User {
    private String id;
    private String name;
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class Vehicle {
    private String id;
    private String name;
}
@AllArgsConstructor
@NoArgsConstructor
@Data
class BookingDuration {

    private Long from;
    private Long to;
}
@AllArgsConstructor
@NoArgsConstructor
@Data
class BookingPrice {
    private BigDecimal price;
}
