package com.linebot.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Builder
@Data
@Entity
@Table(name = "notice")
@NoArgsConstructor
@AllArgsConstructor
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "year")
    private int year;
    @Column(name = "month")
    private int month;
    @Column(name = "day")
    private int day;
    @Column(name = "hour")
    private int hour;
    @Column(name = "minute")
    private int minute;
    @Column(name = "day_of_week")
    private int dayOfWeek;
    @Column(name = "type")
    private int type;
    @Column(name = "deleted")
    private String deleted;
    @Column(name = "created_at")
    private Date createdAt;

    @AllArgsConstructor
    public enum Type {
        TRAIN_DELAY(1),
        CRON(2);
        @Getter
        private int code;
    }
}
