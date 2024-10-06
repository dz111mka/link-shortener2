--liquibase formatted sql
--changeset Dmitriy:1

CREATE TABLE IF NOT EXISTS link_info (
  id UUID PRIMARY KEY,
  link VARCHAR(2048),
  end_time TIMESTAMP,
  description VARCHAR,
  active BOOLEAN,
  short_link VARCHAR(128) UNIQUE,
  opening_count BIGINT,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);