package com.linebot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@Entity
@Table(name = "bot_log")
@NoArgsConstructor
@AllArgsConstructor
public class BotLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "bot_action_name")
    private String botActionName;
    @Column(name = "message")
    private String message;
    @Column(name = "year_month")
    private String yearMonth;
    @Column(name = "day")
    private int day;
    @Column(name = "day_of_week")
    private int dayOfWeek;
    @Column(name = "hour")
    private int hour;
    @Column(name = "minute")
    private int minute;
    @Column(name = "exec_time")
    private Date execTime;

}
