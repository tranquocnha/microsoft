package com.microservices.ratereview.entity;


import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "rate_review", name = "TB_HISTORY_RATE_REVIEW")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryRateReviewEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LOG_ID")
	private int idLog;
	
	@Column(name = "FLAG_REVIEW")
	private int flagReview;
	
	@Column(name = "DATE_REVIEW")
	private Date dateReview;
	
	@Column(name = "NUM_RATE")
	private int numRate;

	@Column(name = "REVIEW_CONTENT")
	private String reviewContent;
	
	// USER
	@Column(name = "USER_ID")
	private String idUser;
	
	@Column(name = "USER_NAME")
	private String userName;

	// Vehicle
	@Column(name = "VEHICLE_ID")
	private String idVehicle;
	
	@Column(name = "VEHICLE_NAME")
	private String vehicleName;

	// Booking
	@Column(name = "BOOKING_ID")
	private String idBooking;
	
	@Column(name = "STATUS_BOOKING")
	private String statusBooking;
	
	@Column(name = "BOOKING_FROM")
	private Long bookingFrom;
	
	@Column(name = "BOOKING_TO")
	private  Long bookingTo;
	
	@Column(name = "BOOKING_PRICE")
	private  Long bookingPrice;
	
	// Payment
	@Column(name = "PAYMENT_STATUS")
    private String paymentStatus;

}
