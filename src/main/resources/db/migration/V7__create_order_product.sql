CREATE TABLE order_product
(
    order_id   UUID NOT NULL REFERENCES orders (id),
    product_id UUID NOT NULL REFERENCES product (id),
    PRIMARY KEY (order_id, product_id)
);

CREATE INDEX idx_order_product_order_id   ON order_product (order_id);
CREATE INDEX idx_order_product_product_id ON order_product (product_id);

