DROP SCHEMA IF EXISTS payment CASCADE;

CREATE SCHEMA payment;

DROP TYPE IF EXISTS payment_status;
CREATE TYPE payment_status AS ENUM ('COMPLETED', 'CANCELLED', 'FAILED');


DROP TABLE IF EXISTS "payment".payments CASCADE;
CREATE TABLE "payment".payments
(
    id uuid NOT NULL,
    user_id uuid NOT NULL,
    amount numeric(10,2) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT payments_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "payment".payments_history CASCADE;
CREATE TABLE "payment".payments_history
(
    id serial,
    payment_id uuid NOT NULL,
    booking_id uuid NOT NULL,
    trace_id uuid NOT NULL,
    amount numeric(10,2) NOT NULL,
    status payment_status NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT payments_history_pkey PRIMARY KEY (id)
);

