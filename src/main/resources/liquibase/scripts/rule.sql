-- liquibase formatted sql

-- changeset dmitri:1
CREATE TABLE request_object (
    id UUID PRIMARY KEY NOT NULL,
    query TEXT NOT NULL,
    request_object_arguments TEXT[] NOT NULL,
    negate BOOLEAN NOT NULL
)
-- changeset sematy:2
CREATE TABLE request_object_argument (
id bigserial PRIMARY KEY NOT NULL,
argument TEXT NOT NULL,
request_object_id UUID not null
)


