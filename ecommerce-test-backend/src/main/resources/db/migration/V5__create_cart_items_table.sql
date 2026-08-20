CREATE TABLE cart_items(
                         id BIGSERIAL PRIMARY KEY,
                         cart_id BIGINT NOT NULL REFERENCES carts(id),
                         product_id BIGINT NOT NULL REFERENCES products(id),
                         quantity INTEGER NOT NULL,
                         total NUMERIC(10,2) NOT NULL,
                         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);