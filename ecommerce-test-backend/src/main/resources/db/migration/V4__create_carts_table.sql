CREATE TABLE carts(
                         id BIGSERIAL PRIMARY KEY,
                         user_id BIGINT NOT NULL REFERENCES users(id),
                         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);