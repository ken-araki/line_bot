package com.linebot.action.traindelay;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TrainDelayTime {
    EXEC_07_00("7:00", 7, 0),
    EXEC_07_20("7:20", 7, 20),
    EXEC_07_40("7:40", 7, 40),
    EXEC_08_00("8:00", 8, 0),
    EXEC_08_20("8:20", 8, 20),
    EXEC_08_40("8:40", 8, 40),
    EXEC_09_00("9:00", 9, 0),
    EXEC_09_20("9:20", 9, 20),
    EXEC_09_40("9:40", 9, 40);

    @Getter
    private String time;

    @Getter
    private int hour;

    @Getter
    private int minute;
}
