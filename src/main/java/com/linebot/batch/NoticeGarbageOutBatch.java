package com.linebot.batch;

import java.util.Collections;
import java.util.concurrent.ExecutionException;

import com.linebot.util.Utils;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.Broadcast;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Controller
public class NoticeGarbageOutBatch {
	private LineMessagingClient lineMessagingClient;

	@Scheduled(cron = "0 20 21 1-7,15-21 * 4", zone = "Asia/Tokyo")
	public void executeResourcesPrevious() {
		log.info("exec executeResourcesPrevious()");
		final BotApiResponse response = Utils.uncheck(() -> {
			return lineMessagingClient.broadcast(new Broadcast(Collections.singletonList(
					new TextMessage("明日は資源ごみの日です。")), false)).get();
		});
		log.info("Sent messages: {}", response);
	}
}
