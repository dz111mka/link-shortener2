--liquibase formatted sql
--changeset Dmitriy:2

CREATE INDEX IF NOT EXISTS idx_end_time ON link_shortener.link_info (end_time);
CREATE INDEX IF NOT EXISTS idx_short_link ON link_shortener.link_info (short_link);
CREATE INDEX IF NOT EXISTS idx_active_create_time ON link_info (active, create_time);


CREATE INDEX IF NOT EXISTS idx_description ON link_shortener.link_info (description);
CREATE INDEX IF NOT EXISTS idx_link ON link_shortener.link_info (link);
