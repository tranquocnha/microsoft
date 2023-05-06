DROP SCHEMA IF EXISTS payment CASCADE;

CREATE SCHEMA payment;

 CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
--
-- DROP TYPE IF EXISTS payment_status;
--
-- CREATE TYPE payment_status AS ENUM ('COMPLETED', 'CANCELLED', 'FAILED');

DROP TABLE IF EXISTS "payment".payments CASCADE;

CREATE TABLE "payment".payments
(
    id uuid NOT NULL,
    customer_id uuid NOT NULL,
    order_id uuid NOT NULL,
    price numeric(10,2) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    --status payment_status NOT NULL,
    CONSTRAINT payments_pkey PRIMARY KEY (id)
);
