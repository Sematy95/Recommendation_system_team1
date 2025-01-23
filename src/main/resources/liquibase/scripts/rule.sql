-- liquibase formatted sql

-- changeset dmitri:1
CREATE TABLE requestObject (
    id UUID PRIMARY KEY NOT NULL,
    query TEXT NOT NULL,
    arguments TEXT[] NOT NULL,
    negate BOOLEAN NOT NULL
)

-- changeset sematy95:2
CREATE TABLE argument (
id BIGSERIAL PRIMARY KEY NOT NULL,
rule_id UUID)


