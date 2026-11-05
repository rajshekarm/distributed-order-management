CREATE TABLE orders (
    id UUID PRIMARY KEY,
    client_order_id VARCHAR(255) NOT NULL,
    symbol VARCHAR(20) NOT NULL,
    side VARCHAR(10) NOT NULL,
    type VARCHAR(10) NOT NULL,
    quantity NUMERIC(19, 6) NOT NULL,
    limit_price NUMERIC(19, 6),
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT uk_orders_client_order_id UNIQUE (client_order_id),
    CONSTRAINT ck_orders_side CHECK (side IN ('BUY', 'SELL')),
    CONSTRAINT ck_orders_type CHECK (type IN ('MARKET', 'LIMIT')),
    CONSTRAINT ck_orders_status CHECK (status IN ('RECEIVED', 'VALIDATED', 'ROUTED', 'PROCESSING', 'FILLED', 'REJECTED', 'CANCELLED')),
    CONSTRAINT ck_orders_quantity_positive CHECK (quantity > 0),
    CONSTRAINT ck_orders_limit_price_positive CHECK (limit_price IS NULL OR limit_price > 0)
);

CREATE INDEX idx_orders_status ON orders (status);
