-- liquibase formatted sql
-- changeset mrvinh:003

USE `notification`;

alter table message
    drop column payload;

alter table message
    add price decimal not null default 0.0;

alter table message
    add payment_status enum ('WAITING', 'SUCCESS', 'FAILURE', 'UNKNOWN') not null default 'UNKNOWN';


