DROP SCHEMA IF EXISTS payment CASCADE;

CREATE SCHEMA payment;

DROP TYPE IF EXISTS payment_status;
CREATE TYPE payment_status AS ENUM ('SUCCESS', 'FAILED', 'WAIT', 'CANCELED');
CREATE TYPE payments_history_operator AS ENUM ('PAY', 'DEPOSIT');


DROP TABLE IF EXISTS "payment".account CASCADE;
CREATE TABLE "payment".account
(
    id serial NOT NULL,
    account VARCHAR(100) NOT NULL,
    amount numeric(10,2) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT payments_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "payment".payments_history CASCADE;
CREATE TABLE "payment".payments_history
(
    id serial,
    account VARCHAR(100) NOT NULL,
    booking_id VARCHAR(100) NULL,
    request_id VARCHAR(100) NOT NULL,
    price numeric(10,2) NOT NULL,
    status payment_status NOT NULL,
    retry INTEGER NOT NULL,
    operator payments_history_operator NOT NULL,
    message VARCHAR(1000) NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT payments_history_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "payment".payments_history_schedule CASCADE;
CREATE TABLE "payment".payments_history_schedule
(
    id serial,
    payments_history_id INTEGER NOT NULL,
    status payment_status NOT NULL,
    message VARCHAR(1000) NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT payments_history_schedule_pkey PRIMARY KEY (id)
);
