-- liquibase formatted sql

-- changeset jordi:1727624200925-3
ALTER TABLE users
    ADD status BOOLEAN;
ALTER TABLE users
    ADD username VARCHAR(50);

-- changeset jordi:1727624200925-4
ALTER TABLE users
    ALTER COLUMN status SET NOT NULL;

-- changeset jordi:1727624200925-6
ALTER TABLE users
    ALTER COLUMN username SET NOT NULL;

-- changeset jordi:1727624200925-7
ALTER TABLE users
    DROP COLUMN user_name;

-- changeset jordi:1727624200925-2
ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);

