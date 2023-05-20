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

	@Column(name = "USER_ID")
	private int idUser;

	@Column(name = "VEHICLE_ID")
	private int idVehicle;

	@Column(name = "BOOKING_ID")
	private int idBooking;

	@Column(name = "NUM_RATE")
	private int numRate;

	@Column(name = "REVIEW_CONTENT")
	private String reviewContent;

	@Column(name = "FLAG_REVIEW")
	private int flagReview;

	@Column(name = "DATE_LOG")
	private Date dateLog;
	
	@Column(name = "PICKUP_TIME")
    private LocalDate pickupTime;
    
	@Column(name = "DROPOFF_TIME")
    private LocalDate dropoffTime;
	
	@Column(name = "LOCATION")
    private String location;
	
	@Column(name = "PAYMENT_METHOD")
    private String paymentMethod;

}
