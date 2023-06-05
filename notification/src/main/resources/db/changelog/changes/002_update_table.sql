-- liquibase formatted sql
-- changeset mrvinh:002

USE `notification`;

alter table message
    add booking_id varchar(50) not null;

alter table message
    add constraint message__udx__receiver__booking_id
        unique (receiver, booking_id);