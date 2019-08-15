package com.line.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class KeepRunningController {

	private static final Logger log = LoggerFactory.getLogger(ScheduleController.class);

	/**
	 * heroku を起動し続ける
	 * 15min でsleep状態になるため、10分ごとに実行する
	 */
	@Scheduled(cron = "0 */10 * * * *")
	public void execute() {
		log.info("keep running heroku.");
	}
}
