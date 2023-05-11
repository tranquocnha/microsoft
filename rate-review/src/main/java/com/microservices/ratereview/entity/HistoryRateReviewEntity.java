package com.microservices.ratereview.entity;


import java.sql.Date;

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
@Table(name = "TB_HISTORY_RATE_REVIEW")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryRateReviewEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_log")
	private int idLog;

	@Column(name = "ID_USER")
	private int idUser;

	@Column(name = "ID_VEHICLE")
	private int idVehicle;

	@Column(name = "ID_BOOKING")
	private int idBooking;

	@Column(name = "NUM_RATE")
	private int numRate;

	@Column(name = "REVIEW_CONTENT")
	private String reviewContent;

	@Column(name = "FLAG_REVIEW")
	private int flagReview;

	@Column(name = "DATE_LOG")
	private Date dateLog;

}
