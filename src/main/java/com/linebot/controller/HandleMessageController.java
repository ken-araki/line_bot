package com.linebot.controller;

import org.springframework.web.bind.annotation.RestController;

import com.linebot.service.ManageTobuyService;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

@RestController
@LineMessageHandler
public class HandleMessageController {
	private ManageTobuyService manageTobuyService;
	
	public HandleMessageController(ManageTobuyService manageTobuyService) {
		this.manageTobuyService = manageTobuyService;
	}
	@EventMapping
	public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws Exception {
		manageTobuyService.execute(event.getReplyToken(), event, event.getMessage());
	}
}
