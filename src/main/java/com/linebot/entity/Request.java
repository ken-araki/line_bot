package com.linebot.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Builder
@Data
@Entity
@Table(name = "request")
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "request")
    private String request;
    @Column(name = "status")
    private int status;
    @Column(name = "deleted")
    private String deleted;
    @Column(name = "created_at")
    private Date createdAt;

}
