package com.line;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;

@SpringBootApplication
public class LineApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LineApiApplication.class, args);
	}
	
	@EventMapping
	public Message handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
		// System.out.println("event: " + event);
		final String originalMessageText = event.getMessage().getText();
		return new TextMessage(originalMessageText);
	}

	@EventMapping
	public void handleDefaultMessageEvent(Event event) {
		// System.out.println("event: " + event);
	}
}
