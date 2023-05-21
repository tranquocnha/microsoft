drop schema if exists rate_review cascade;

create schema rate_review;

drop table if exists rate_review.tb_history_rate_review;

create table if not exists rate_review.tb_history_rate_review
(
    log_id serial not null,
    flag_review integer,
    date_review date,
    num_rate integer,
    review_content character varying(255),
    user_id character varying(255),
    user_name character varying(255),
    vehicle_id character varying(255),
    vehicle_name character varying(255),
    booking_id character varying(255),
    status_booking character varying(255),
    booking_from double precision,
    booking_to double precision,
    booking_price real,
    payment_status character varying(255),
    constraint tb_history_rate_review_pkey primary key (log_id)
)