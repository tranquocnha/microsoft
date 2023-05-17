DROP SCHEMA IF EXISTS payment CASCADE;

CREATE SCHEMA payment;

DROP TYPE IF EXISTS payment_status;
CREATE TYPE payment_status AS ENUM ('SUCCESS', 'FAILED');
CREATE TYPE payments_history_operator AS ENUM ('PAY', 'DEPOSIT');


DROP TABLE IF EXISTS "payment".account CASCADE;
CREATE TABLE "payment".account
(
    id serial NOT NULL,
    user_id VARCHAR(100) NOT NULL,
    user_name VARCHAR(100) NULL,
    amount numeric(10,2) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT payments_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "payment".payments_history CASCADE;
CREATE TABLE "payment".payments_history
(
    id serial,
    account_id integer NOT NULL,
    booking_id VARCHAR(100) NULL,
    request_id VARCHAR(100) NOT NULL,
    price numeric(10,2) NOT NULL,
    status payment_status NOT NULL,
    operator payments_history_operator NOT NULL,
    message VARCHAR(1000) NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT payments_history_pkey PRIMARY KEY (id)
);

