INSERT INTO rate_review.tb_history_rate_review(
	 date_log, dropoff_time, flag_review, id_booking, id_user, id_vehicle, location, num_rate, payment_method, pickup_time, review_content)
	VALUES (now(), now(), 1, 10002, 10003, 10004, 'da nang', 5, 'ATM', now(), 'xe ngon');