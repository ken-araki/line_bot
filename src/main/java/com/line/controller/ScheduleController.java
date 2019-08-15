package com.line.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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
public class ScheduleController {

	private static final Logger log = LoggerFactory.getLogger(ScheduleController.class);
	private static final SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/DD(E) HH:mm:ss");
	private final LineProperties lineProperties;
	private final LineMessagingClient lineMessagingClient;

	ScheduleController(LineProperties lineProperties, LineMessagingClient lineMessagingClient) {
		this.lineProperties = lineProperties;
		this.lineMessagingClient = lineMessagingClient;
	}

	@Scheduled(cron = "0 0 23 1-7,15-21 * 4")
	public void pushAlert() {
		try {
			log.info("exec pushAlert(). date: " + sdf.format(new Date()));
			final BotApiResponse response = lineMessagingClient.pushMessage(new PushMessage(lineProperties.getId(),
					new TemplateMessage("明日は資源ごみの日です。",
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
	
	@Scheduled(cron = "0 0 8 1-7,15-21 * 5")
	public void remaindAlert() {
		try {
			log.info("exec pushAlert(). date: " + sdf.format(new Date()));
			final BotApiResponse response = lineMessagingClient.pushMessage(new PushMessage(lineProperties.getId(),
					new TemplateMessage("今日は資源ごみの日です。 日付：" + sdf.format(new Date()),
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

	@Scheduled(cron = "0 30 18 * * *")
	public void remaindTest() {
		try {
			log.info("exec pushAlert(). date: " + sdf.format(new Date()));
			final BotApiResponse response = lineMessagingClient.pushMessage(
					new PushMessage(lineProperties.getId(), new TemplateMessage("test 日付：" + sdf.format(new Date()), null)
					)
			).get();
			log.info("Sent messages: {}", response);
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}
}
