package com.microservices.ratereview.dto;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Data;

@Data
public class HistoryRateReviewDTO {
	private int flagReview;
	private Date dateReview;
	private int numRate;
	private String reviewContent;
	// USER
	private String idUser;
	private String userName;
	// Vehicle
	private String idVehicle;
	private String vehicleName;
	// Booking
	private String idBooking;
	private String statusBooking;
	private Long bookingFrom;
	private Long bookingTo;
	private BigDecimal bookingPrice;
	// Payment
    private String paymentStatus;
}


