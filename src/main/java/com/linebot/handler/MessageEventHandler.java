package com.linebot.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import com.linebot.action.ManageTobuyAction;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

@Slf4j
@AllArgsConstructor
@RestController
@LineMessageHandler
public class MessageEventHandler {
	private ManageTobuyAction manageTobuyAction;

	@EventMapping
	public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws Exception {
		manageTobuyAction.execute(event.getReplyToken(), event, event.getMessage());
	}
}
