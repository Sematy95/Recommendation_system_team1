-- liquibase formatted sql

-- changeset dmitri:1
CREATE TABLE rule (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    arguments TEXT[] NOT NULL,
    negate BOOLEAN NOT NULL
)

-- changeset dmitri:2
ALTER TABLE rule
ADD query TEXT

-- changeset dmitri:3
DROP TABLE rule

-- changeset dmitri:4
CREATE TABLE rules (
    id UUID PRIMARY KEY NOT NULL,
    query TEXT NOT NULL,
    arguments TEXT[] NOT NULL,
    negate BOOLEAN NOT NULL
)