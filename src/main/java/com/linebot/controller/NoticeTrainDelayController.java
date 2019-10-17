package com.linebot.controller;

import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.linebot.entry.TrainDelay;
import com.linebot.service.GetTrainDelayResourceService;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.Broadcast;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Controller
public class NoticeTrainDelayController {
	private final String LINE_SEPARATOR = System.getProperty("line.separator");

	private LineMessagingClient lineMessagingClient;
	private GetTrainDelayResourceService getTrainDelayResourceService;

	@Scheduled(cron = "0 5 8 * * *", zone = "Asia/Tokyo")
	public void executeJrEast() {
		StringBuilder sb = new StringBuilder(128);
		TrainDelay[] list = getTrainDelayResourceService.getDelay();
		if (list.length > 0) {
			sb.append("遅延している沿線は以下です。");
			Stream.of(list).forEach(l -> sb.append(LINE_SEPARATOR).append(l.getName()));
		} else {
			sb.append("遅延している沿線はありません。");
		}

		try {
			log.info("exec executeJrEast()");
			final BotApiResponse response = lineMessagingClient.broadcast(
					new Broadcast(Collections.singletonList(new TextMessage(sb.toString())), false)
			).get();
			log.info("Sent messages: {}", response);
		} catch (InterruptedException | ExecutionException e) {
			log.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}
}
