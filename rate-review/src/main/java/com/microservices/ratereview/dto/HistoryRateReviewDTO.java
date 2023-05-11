package com.microservices.ratereview.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class HistoryRateReviewDTO {
	private int idLog;
	private int idUser;
	private int idVehicle;
	private int idBooking;
	private int numRate;
	private String reviewContent;
	private int flagReview;
	private Date dateLog;

}
