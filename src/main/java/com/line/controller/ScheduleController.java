package com.line.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.springframework.scheduling.annotation.Scheduled;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.template.ConfirmTemplate;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

@LineMessageHandler
public class ScheduleController {
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	private final LineMessagingClient lineMessagingClient;

	ScheduleController(LineMessagingClient lineMessagingClient) {
		this.lineMessagingClient = lineMessagingClient;
	}
	@Scheduled(cron = "50-59 0 * * * *", zone = "Asia/Tokyo")
	public void execute() {
		try {
			lineMessagingClient.pushMessage(new PushMessage("${ark.myUserId}",
					new TemplateMessage("test Message: " + sdf.format(new Date()),
							new ConfirmTemplate("are you ok？",
									new MessageAction("yes", "no"),
									new MessageAction("Yes", "No")
							)
					)
			)).get();
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}
}
