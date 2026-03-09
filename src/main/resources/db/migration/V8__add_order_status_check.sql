-- V8: Document new OrderStatus values STANDBY and DELETED for status_order column.
-- The column is VARCHAR(30), so no ALTER TYPE is needed.
-- Adding a CHECK constraint to enforce only valid enum values.

ALTER TABLE orders
    DROP CONSTRAINT IF EXISTS orders_status_order_check;

ALTER TABLE orders
    ADD CONSTRAINT orders_status_order_check
        CHECK (status_order IN (
            'WAITING',
            'ON_THE_WAY',
            'ARRIVING',
            'DELIVERED',
            'STANDBY',
            'DELETED',
            'CANCELLED'
        ));

