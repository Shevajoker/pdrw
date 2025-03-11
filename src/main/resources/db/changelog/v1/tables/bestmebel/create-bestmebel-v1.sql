-- liquibase formatted sql

-- changeset Nikita:1739736974094
CREATE TABLE IF NOT EXISTS bestmebel_ru
(
    id          UUID NOT NULL,
    article     VARCHAR(255),
    name        VARCHAR(255),
    image       VARCHAR(255),
    price_new   DECIMAL,
    price_old   DECIMAL,
    discount    DECIMAL,
    date_create TIMESTAMP WITHOUT TIME ZONE,
    date_update TIMESTAMP WITHOUT TIME ZONE,
    type        VARCHAR(255),
    actual      BOOLEAN DEFAULT false,
    link        VARCHAR(255),
    CONSTRAINT pk_bestmebel_ru PRIMARY KEY (id)
);

