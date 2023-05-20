package com.microservices.ratereview.dto;

import java.sql.Date;
import java.time.LocalDate;

import lombok.Data;

@Data
public class HistoryRateReviewDTO {
	private int logId;
	private int userId;
	private int vehicleId;
	private int bookingId;
	private int numRate;
	private String reviewContent;
	private int flagReview;
	private Date dateLog;
    private LocalDate pickupTime;
    private LocalDate dropoffTime;
    private String location;
    private String paymentMethod;

}
