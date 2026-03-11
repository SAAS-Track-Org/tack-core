CREATE TABLE delivery
(
    id                      UUID PRIMARY KEY,
    public_code_client      UUID        NOT NULL UNIQUE,
    public_code_deliveryman UUID        NOT NULL UNIQUE,
    status                  VARCHAR(20) NOT NULL DEFAULT 'CREATED',
    deliveryman_id          UUID        NOT NULL REFERENCES deliveryman (id),
    app_user_id             UUID        NOT NULL REFERENCES app_user (id),
    current_lat             DECIMAL(9, 6),
    current_lng             DECIMAL(9, 6),
    created_at              TIMESTAMP   NOT NULL DEFAULT NOW(),
    updated_at              TIMESTAMP   NOT NULL DEFAULT NOW(),
    delivered_at            TIMESTAMP,

    CONSTRAINT chk_delivery_status
        CHECK (status IN ('CREATED', 'IN_TRANSIT', 'DELIVERED', 'CANCELLED'))
);

CREATE INDEX idx_delivery_public_code_client ON delivery (public_code_client);
CREATE INDEX idx_delivery_public_code_deliveryman ON delivery (public_code_deliveryman);
CREATE INDEX idx_delivery_status ON delivery (status);
CREATE INDEX idx_delivery_deliveryman_id ON delivery (deliveryman_id);
CREATE INDEX idx_delivery_app_user_id ON delivery (app_user_id);
