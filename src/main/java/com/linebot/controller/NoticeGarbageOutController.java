package com.linebot.controller;

import java.util.Collections;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.Broadcast;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class NoticeGarbageOutController {
	private static final Logger log = LoggerFactory.getLogger(NoticeGarbageOutController.class);
	private final LineMessagingClient lineMessagingClient;

	NoticeGarbageOutController(LineMessagingClient lineMessagingClient) {
		this.lineMessagingClient = lineMessagingClient;
	}

	@Scheduled(cron = "0 20 21 1-7,15-21 * 4", zone = "Asia/Tokyo")
	public void executeResourcesPrevious() {
		try {
			log.info("exec executeResourcesPrevious()");
			final BotApiResponse response = lineMessagingClient.broadcast(
					new Broadcast(Collections.singletonList(new TextMessage("明日は資源ごみの日です。")), false)
			).get();
			log.info("Sent messages: {}", response);
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}
}
