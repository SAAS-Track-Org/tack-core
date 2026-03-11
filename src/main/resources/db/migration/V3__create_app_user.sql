CREATE TABLE app_user
(
    id                   UUID         PRIMARY KEY DEFAULT gen_random_uuid(),
    email                VARCHAR(150) NOT NULL UNIQUE,
    establishment_name   VARCHAR(255),
    address              VARCHAR(500),
    created_at           TIMESTAMP    NOT NULL DEFAULT NOW()
);

