-- Baseline: schema as it existed before Flyway was introduced.
-- This file is not executed (baseline-version=1 marks it as already applied),
-- but documents the starting schema for future reference.

CREATE TABLE IF NOT EXISTS users (
                                     id BIGSERIAL PRIMARY KEY,
                                     username VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) CHECK (role IN ('USER', 'ADMIN')),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    phone_number VARCHAR(255),
    department VARCHAR(255) CHECK (department IN ('HR','FINANCE','IT','LEGAL','MARKETING','OPERATIONS','OTHER')),
    gender VARCHAR(255) CHECK (gender IN ('MALE','FEMALE','OTHER')),
    age INTEGER,
    created_at TIMESTAMP NOT NULL
    );

CREATE TABLE IF NOT EXISTS documents (
                                         id BIGSERIAL PRIMARY KEY,
                                         filename VARCHAR(255) NOT NULL,
    content_type VARCHAR(255) NOT NULL,
    size BIGINT NOT NULL,
    data BYTEA NOT NULL,
    uploaded_at TIMESTAMP NOT NULL,
    owner_id BIGINT NOT NULL REFERENCES users(id)
    );

CREATE TABLE IF NOT EXISTS tags (
                                    id BIGSERIAL PRIMARY KEY,
                                    name VARCHAR(255) UNIQUE NOT NULL
    );

CREATE TABLE IF NOT EXISTS document_tags (
                                             document_id BIGINT NOT NULL REFERENCES documents(id),
    tag_id BIGINT NOT NULL REFERENCES tags(id),
    PRIMARY KEY (document_id, tag_id)
    );