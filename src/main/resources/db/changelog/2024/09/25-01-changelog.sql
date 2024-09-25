-- liquibase formatted sql

-- changeset jordi:1727302837645-1
CREATE TABLE permissions
(
    id   UUID        NOT NULL,
    name VARCHAR(20) NOT NULL,
    CONSTRAINT pk_permissions PRIMARY KEY (id)
);

-- changeset jordi:1727302837645-2
CREATE TABLE role_has_permissions
(
    role_id       UUID NOT NULL,
    permission_id UUID NOT NULL,
    CONSTRAINT pk_role_has_permissions PRIMARY KEY (role_id, permission_id)
);

-- changeset jordi:1727302837645-3
CREATE TABLE roles
(
    id   UUID        NOT NULL,
    name VARCHAR(15) NOT NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

-- changeset jordi:1727302837645-4
CREATE TABLE syslog
(
    id          UUID         NOT NULL,
    action      VARCHAR(15),
    description VARCHAR(200),
    by_user     UUID,
    timestamp   VARCHAR(255) NOT NULL,
    CONSTRAINT pk_syslog PRIMARY KEY (id)
);

-- changeset jordi:1727302837645-5
CREATE TABLE user_has_roles
(
    role_id UUID NOT NULL,
    user_id UUID NOT NULL
);

-- changeset jordi:1727302837645-6
CREATE TABLE users
(
    id           UUID         NOT NULL,
    first_name   VARCHAR(50),
    last_name    VARCHAR(255),
    user_name    VARCHAR(50)  NOT NULL,
    photo_url    VARCHAR(255),
    email        VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20),
    password     VARCHAR(80),
    CONSTRAINT pk_users PRIMARY KEY (id)
);

-- changeset jordi:1727302837645-7
ALTER TABLE user_has_roles
    ADD CONSTRAINT pk_user_has_roles PRIMARY KEY (user_id, role_id);

-- changeset jordi:1727302837645-8
ALTER TABLE permissions
    ADD CONSTRAINT uc_permissions_name UNIQUE (name);

-- changeset jordi:1727302837645-9
ALTER TABLE roles
    ADD CONSTRAINT uc_roles_name UNIQUE (name);

-- changeset jordi:1727302837645-10
ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

-- changeset jordi:1727302837645-11
ALTER TABLE users
    ADD CONSTRAINT uc_users_phonenumber UNIQUE (phone_number);

-- changeset jordi:1727302837645-12
ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (user_name);

-- changeset jordi:1727302837645-13
ALTER TABLE role_has_permissions
    ADD CONSTRAINT FK_ROLE_HAS_PERMISSIONS_ON_PERMISSION FOREIGN KEY (permission_id) REFERENCES permissions (id);

-- changeset jordi:1727302837645-14
ALTER TABLE role_has_permissions
    ADD CONSTRAINT FK_ROLE_HAS_PERMISSIONS_ON_ROLE FOREIGN KEY (role_id) REFERENCES roles (id);

-- changeset jordi:1727302837645-15
ALTER TABLE syslog
    ADD CONSTRAINT FK_SYSLOG_ON_BY_USER FOREIGN KEY (by_user) REFERENCES users (id);

-- changeset jordi:1727302837645-16
ALTER TABLE user_has_roles
    ADD CONSTRAINT FK_USER_HAS_ROLES_ON_ROLE FOREIGN KEY (role_id) REFERENCES roles (id);

-- changeset jordi:1727302837645-17
ALTER TABLE user_has_roles
    ADD CONSTRAINT FK_USER_HAS_ROLES_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

