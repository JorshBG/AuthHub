-- liquibase formatted sql

-- changeset jordi:1728685380502-1
ALTER TABLE syslog
    DROP COLUMN timestamp;

-- changeset jordi:1728685380502-2
ALTER TABLE syslog
    ADD timestamp TIMESTAMP WITHOUT TIME ZONE NOT NULL;

