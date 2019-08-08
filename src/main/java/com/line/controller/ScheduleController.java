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
	private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	private final LineProperties lineProperties;
	private final LineMessagingClient lineMessagingClient;

	ScheduleController(LineProperties lineProperties, LineMessagingClient lineMessagingClient) {
		this.lineProperties = lineProperties;
		this.lineMessagingClient = lineMessagingClient;
	}

	@Scheduled(cron = "0 */5 * * * *")
	public void execute() {
		try {
			final BotApiResponse response = lineMessagingClient.pushMessage(new PushMessage(lineProperties.getId(),
					new TemplateMessage("test Message: " + sdf.format(new Date()),
							new ConfirmTemplate("are you ok？",
									new MessageAction("yes", "no"),
									new MessageAction("Yes", "No")
							)
					)
			)).get();
			log.info("Sent messages: {}", response);
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}
}
