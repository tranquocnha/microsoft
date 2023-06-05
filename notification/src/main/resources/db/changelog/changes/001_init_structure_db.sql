-- liquibase formatted sql
-- changeset mrvinh:001

# DROP DATABASE IF EXISTS `notification`;
# CREATE DATABASE `notification` DEFAULT CHARACTER SET utf8mb4;
USE `notification`;

drop table if exists message;
create table if not exists `notification`.message
(
    id           bigint auto_increment primary key                           not null,
    sender       varchar(100)                                                not null,
    receiver     varchar(100)                                                not null,
    payload      varchar(5000)                                               not null,
    message_type enum ('ORDER_PAYMENT', 'MAIL', 'UNKNOWN') default 'UNKNOWN' not null,
    consumed     tinyint(1)                                default 0         not null,
    created_at   datetime                                                    null,
    updated_at   datetime                                                    null,
    created_by   bigint                                                      null,
    updated_by   bigint                                                      null
);
