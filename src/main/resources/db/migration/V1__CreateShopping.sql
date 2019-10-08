CREATE TABLE tobuy (
    id SERIAL NOT NULL,
    goods VARCHAR(255) NOT NULL,
    is_completed VARCHAR(1) NOT NULL,
    created_date TIMESTAMP,
    updated_date TIMESTAMP,
    PRIMARY KEY (id)
);