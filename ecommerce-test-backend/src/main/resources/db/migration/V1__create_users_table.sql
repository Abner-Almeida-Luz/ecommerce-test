CREATE TABLE users(
                      id BIGSERIAL PRIMARY KEY,
                      username VARCHAR(100) NOT NULL,
                      login VARCHAR(100) UNIQUE NOT NULL,
                      password VARCHAR(255) NOT NULL,
                      role VARCHAR(20) NOT NULL CHECK ( role IN ('ADMIN','USERS')),
                      created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);