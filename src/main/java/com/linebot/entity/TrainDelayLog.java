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
@Table(name = "train_delay_log")
@NoArgsConstructor
@AllArgsConstructor
public class TrainDelayLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "train_name")
    private String trainName;
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
