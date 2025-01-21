-- liquibase formatted sql

-- changeset dmitri:1
CREATE TABLE rule (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    arguments TEXT[] NOT NULL,
    negate BOOLEAN NOT NULL
)