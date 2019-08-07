package com.line.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;

import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

@LineMessageHandler
public class ScheduleController {
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	
	@Scheduled(cron = "25-30 0 * * * *", zone = "Asia/Tokyo")
	public TextMessage execute() {
		return new TextMessage(sdf.format(new Date()));
	}
}
