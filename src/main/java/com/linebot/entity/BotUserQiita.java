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
@Table(name = "bot_user_qiita")
@NoArgsConstructor
@AllArgsConstructor
public class BotUserQiita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "qiita_user_id")
    private String qiitaUserId;
    @Column(name = "deleted")
    private String deleted;
    @Column(name = "created_date")
    private Date createdDate;
}
