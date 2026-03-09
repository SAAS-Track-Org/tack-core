CREATE TABLE orders
(
    id              UUID        PRIMARY KEY,
    code            VARCHAR(20) NOT NULL UNIQUE,
    delivery_id     UUID        NOT NULL REFERENCES delivery (id),
    client_id       UUID        NOT NULL REFERENCES client   (id),
    address_id      UUID             REFERENCES address  (id),
    notes           VARCHAR(500),
    address_status  VARCHAR(50) NOT NULL DEFAULT 'ADDRESS_PENDING',
    status_order    VARCHAR(30) NOT NULL DEFAULT 'WAITING',
    total_amount    DECIMAL(10, 2),
    payment_method  VARCHAR(30),
    created_at      TIMESTAMP   NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP   NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_orders_delivery_id ON orders (delivery_id);
CREATE INDEX idx_orders_client_id ON orders (client_id);

CREATE SEQUENCE IF NOT EXISTS order_code_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

