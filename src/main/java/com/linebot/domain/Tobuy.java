package com.linebot.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tobuy {
	private String id;
	private String goods;
	private String isCompleted;
	private Date createdDate;
	private Date updatedDate;
}
