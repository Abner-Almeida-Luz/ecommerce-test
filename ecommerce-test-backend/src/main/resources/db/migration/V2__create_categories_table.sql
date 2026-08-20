CREATE TABLE categories(
                      id BIGSERIAL PRIMARY KEY,
                      name VARCHAR(100) UNIQUE NOT NULL,
                      description VARCHAR(255) NOT NULL,
                      created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);