-- だれが、どのbotを、いつ（月、日、時間帯）ぐらいを把握しようと思う
CREATE TABLE bot_log (
    id SERIAL NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    bot_action_name VARCHAR(255) NOT NULL,
    message VARCHAR(255) NOT NULL,
    year_month VARCHAR(6) NOT NULL,
    "day" INTEGER NOT NULL,
    "day_of_week" INTEGER NOT NULL,
    "hour" INTEGER NOT NULL,
    "minute" INTEGER NOT NULL,
    exec_time TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
);