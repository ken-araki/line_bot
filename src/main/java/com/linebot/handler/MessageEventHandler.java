package com.linebot.handler;

import com.linebot.action.ActionHandler;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.UnfollowEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@LineMessageHandler
public class MessageEventHandler {
    private ActionHandler handler;

    @EventMapping
    public List<Message> handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws Exception {
        String userId = event.getSource().getUserId();
        String message = event.getMessage().getText();

        log.info("callback. userId: {}, message: {}", userId, message);
        return handler.handle(userId, message);
    }

    @EventMapping
    public List<Message> handleFollowEvent(FollowEvent event) throws Exception {
        String userId = event.getSource().getUserId();

        log.info("follow. userId: {}", userId);
        return handler.follow(userId);
    }
    @EventMapping
    public void handleUnfollowEvent(UnfollowEvent event) {
        String userId = event.getSource().getUserId();

        log.info("unfollowed this bot: {}", userId);
        handler.unfollow(userId);
    }
}
