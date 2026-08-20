CREATE TABLE products(
                           id BIGSERIAL PRIMARY KEY,
                           category_id BIGINT NOT NULL REFERENCES categories(id),
                           name VARCHAR(100) NOT NULL,
                           description VARCHAR(255) NOT NULL,
                           price NUMERIC(10,2) NOT NULL,
                           stock INTEGER NOT NULL,
                           image_url VARCHAR(255) NOT NULL,
                           created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);