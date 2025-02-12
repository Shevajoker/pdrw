--liquibase formatted sql

--changeset rniyazov:1
CREATE TABLE IF NOT EXISTS subscription
(
    id          UUID PRIMARY KEY,
    email       VARCHAR(64) UNIQUE         NOT NULL,
    format      VARCHAR(12)                NOT NULL,
    frequency   VARCHAR(16)                NOT NULL,
    user_id     UUID REFERENCES users (id) NOT NULL,
    active      BOOLEAN                    NOT NULL DEFAULT FALSE,
    create_date TIMESTAMP                  NOT NULL
);
