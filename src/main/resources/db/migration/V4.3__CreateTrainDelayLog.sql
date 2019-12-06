-- どの沿線が、何曜日の、どの時間帯で、遅延しやすいかわかればいい
CREATE TABLE train_delay_log (
    id SERIAL NOT NULL,
    train_name VARCHAR(255) NOT NULL,
    year_month VARCHAR(6) NOT NULL,
    "day" INTEGER NOT NULL,
    "day_of_week" INTEGER NOT NULL,
    "hour" INTEGER NOT NULL,
    "minute" INTEGER NOT NULL,
    exec_time TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
);