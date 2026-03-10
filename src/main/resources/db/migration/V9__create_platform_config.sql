CREATE TABLE platform_config
(
    id                     UUID      PRIMARY KEY DEFAULT gen_random_uuid(),
    session_duration_hours INTEGER   NOT NULL DEFAULT 8,
    created_at             TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at             TIMESTAMP NOT NULL DEFAULT NOW()
);

-- Insert singleton record
INSERT INTO platform_config (session_duration_hours)
VALUES (8);

