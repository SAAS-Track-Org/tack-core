CREATE TABLE client
(
    id           UUID PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20)  NOT NULL,
    created_at   TIMESTAMP    NOT NULL DEFAULT NOW()
);