DROP SCHEMA IF EXISTS rate_review CASCADE;

CREATE SCHEMA rate_review;

DROP TABLE IF EXISTS rate_review.tb_history_rate_review;

CREATE TABLE IF NOT EXISTS rate_review.tb_history_rate_review
(
    log_id SERIAL NOT NULL,
    date_log date,
    dropoff_time date,
    flag_review integer,
    booking_id character varying(255),
    user_id character varying(255),
    vehicle_id character varying(255),
    location character varying(255),
    num_rate integer,
    payment_method character varying(255),
    pickup_time date,
    review_content character varying(255),
    CONSTRAINT tb_history_rate_review_pkey PRIMARY KEY (log_id)
)