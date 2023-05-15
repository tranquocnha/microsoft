DROP SCHEMA IF EXISTS rate_review CASCADE;

CREATE SCHEMA rate_review;

DROP TABLE IF EXISTS rate_review.tb_history_rate_review;

CREATE TABLE IF NOT EXISTS rate_review.tb_history_rate_review
(
    id_log SERIAL NOT NULL,
    date_log date,
    dropoff_time date,
    flag_review integer,
    id_booking integer,
    id_user integer,
    id_vehicle integer,
    location character varying(255) COLLATE pg_catalog."default",
    num_rate integer,
    payment_method character varying(255) COLLATE pg_catalog."default",
    pickup_time date,
    review_content character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT tb_history_rate_review_pkey PRIMARY KEY (id_log)
)