package com.linebot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "bot_user")
@NoArgsConstructor
@AllArgsConstructor
public class BotUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "deleted")
    private String deleted;
    @Column(name = "created_date")
    private Date createdDate;
}
