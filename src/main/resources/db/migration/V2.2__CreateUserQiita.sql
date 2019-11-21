CREATE TABLE bot_user_qiita (
    id SERIAL NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    qiita_user_id VARCHAR(255) NOT NULL,
    deleted VARCHAR(1) NOT NULL DEFAULT '0',
    created_date TIMESTAMP,
    PRIMARY KEY (id)
);