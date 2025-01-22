--liquibase formatted sql

--changeset rniyazov:1
create table dashboard
(
    id SERIAL PRIMARY KEY,
    store_name VARCHAR(32) UNIQUE NOT NULL ,
    total_products INTEGER ,
    total_price_without_discounts NUMERIC,
    total_price_with_discounts NUMERIC,
    average_discount_percentage NUMERIC,
    price_mode NUMERIC,
    average_price NUMERIC,
    most_popular_category_name VARCHAR(150),
    most_popular_category_count INTEGER,
    least_popular_category_name VARCHAR(150),
    least_popular_category_count INTEGER,
    max_discount_percentage NUMERIC,
    max_product_url TEXT,
    min_discount_percentage NUMERIC,
    min_product_url TEXT,
    discounted_products_percentage NUMERIC,
    total_deleted_products INTEGER,
    total_updated_products INTEGER,
    last_date_create TIMESTAMP,
    last_date_update TIMESTAMP
);