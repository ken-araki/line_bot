-- userは予約語なので、とりあえずbotを前につけた
CREATE TABLE bot_user (
    id SERIAL NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    deleted VARCHAR(1) NOT NULL DEFAULT '0',
    created_date TIMESTAMP,
    PRIMARY KEY (id)
);