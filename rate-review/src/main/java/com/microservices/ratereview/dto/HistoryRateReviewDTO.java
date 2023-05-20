package com.microservices.ratereview.dto;

import java.sql.Date;
import java.time.LocalDate;

import lombok.Data;

@Data
public class HistoryRateReviewDTO {
	private int logId;
	private String userId;
	private String vehicleId;
	private String bookingId;
	private int numRate;
	private String reviewContent;
	private int flagReview;
	private Date dateLog;
    private LocalDate pickupTime;
    private LocalDate dropoffTime;
    private String location;
    private String paymentMethod;

}
