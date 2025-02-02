-- liquibase formatted sql

-- changeset dmitri:1
create sequence arguments_seq start with 1 increment by 1;
create sequence condition_seq start with 1 increment by 1;
create sequence dynamic_rule_seq start with 1 increment by 1;
CREATE TABLE IF NOT EXISTS dynamic_rule  (
    id BIGINT PRIMARY KEY NOT NULL,
    product_name TEXT NOT NULL,
    product_id UUID NOT NULL,
    product_text TEXT NOT NULL,
    conditions varchar(255) array);

CREATE TABLE IF NOT EXISTS condition (
    id BIGINT PRIMARY KEY NOT NULL,
    query TEXT NOT NULL,
    arguments_id BIGINT NOT NULL,
    negate BOOLEAN NOT NULL);

CREATE TABLE IF NOT EXISTS arguments (
  id BIGINT PRIMARY KEY NOT NULL,
  product_Type TEXT,
  transaction_Name TEXT,
  compare_Type TEXT,
  compare_Value TEXT);

CREATE TABLE IF NOT EXISTS dynamic_rule_conditions (
  dynamic_rule_id BIGINT NOT NULL,
  conditions_id BIGINT NOT NULL
);