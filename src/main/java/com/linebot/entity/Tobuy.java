package com.linebot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "tobuy")
@NoArgsConstructor
@AllArgsConstructor
public class Tobuy {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "goods")
	private String goods;
	@Column(name = "is_completed")
	private String isCompleted;
	@Column(name = "created_date")
	private Date createdDate;
	@Column(name = "updated_date")
	private Date updatedDate;

	// postgres は列追加の場所を指定できない
	@Column(name = "user_id")
	private String userId;
}
