package com.line.controller;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.line.config.LineProperties;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.template.ConfirmTemplate;
import com.linecorp.bot.model.response.BotApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class NoticeGarbageOutController {

	private static final Logger log = LoggerFactory.getLogger(NoticeGarbageOutController.class);
	private final LineProperties lineProperties;
	private final LineMessagingClient lineMessagingClient;

	NoticeGarbageOutController(LineProperties lineProperties, LineMessagingClient lineMessagingClient) {
		this.lineProperties = lineProperties;
		this.lineMessagingClient = lineMessagingClient;
	}

	/**
	 * 「資源ごみ」の前日夜に通知を出す
	 */
	@Scheduled(cron = "0 0 21-23 1-7,15-21 * 4", zone = "Asia/Tokyo")
	public void executeResourcesPrevious() {
		try {
			log.info("exec executeResourcesPrevious()");
			final BotApiResponse response = lineMessagingClient.pushMessage(
					new PushMessage(lineProperties.getId(),new TemplateMessage("明日は資源ごみの日です。", null))
			).get();
			log.info("Sent messages: {}", response);
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 「資源ごみ」の当日朝に通知を出す
	 */
	@Scheduled(cron = "0 0 8 1-7,15-21 * 5", zone = "Asia/Tokyo")
	public void executeResources() {
		try {
			log.info("exec executeResources()");
			final BotApiResponse response = lineMessagingClient.pushMessage(new PushMessage(lineProperties.getId(),
					new TemplateMessage("今日は資源ごみの日です。",
							new ConfirmTemplate("準備はいいですか？",
									new MessageAction("はい", "大丈夫です"),
									new MessageAction("いいえ", "忘れてました")
							)
					)
			)).get();
			log.info("Sent messages: {}", response);
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}
}
