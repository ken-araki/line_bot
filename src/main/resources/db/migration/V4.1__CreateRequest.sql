CREATE TABLE request (
    id SERIAL NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    request VARCHAR(400) NOT NULL,
    status INTEGER NOT NULL DEFAULT '0',
    deleted VARCHAR(1) NOT NULL DEFAULT '0',
    created_at TIMESTAMP,
    PRIMARY KEY (id)
);