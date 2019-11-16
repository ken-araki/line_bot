package com.linebot.service.message;

import com.linebot.util.Utils;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.Broadcast;
import com.linecorp.bot.model.Multicast;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.response.BotApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class PushMessageService {
    private LineMessagingClient lineMessagingClient;

    public BotApiResponse broadcast(List<Message> messages) {
        log.info("send broadcast. messages: {}", messages);
        return Utils.uncheck(() -> {
            BotApiResponse response = lineMessagingClient.broadcast(new Broadcast(messages, false)).get();
            log.info("broadcast result. {}", response);
            return response;
        });
    }

    public BotApiResponse multicast(Set<String> userIds, List<Message> messages) {
        log.info("send multicast. userIds: {}, messages: {}", userIds, messages);
        return Utils.uncheck(() -> {
            BotApiResponse response = lineMessagingClient.multicast(new Multicast(userIds, messages)).get();
            log.info("multicast result. {}", response);
            return response;
        });
    }

    public BotApiResponse pushMessage(String userId, List<Message> messages) {
        log.info("send pushMessage. userId: {}, messages: {}", userId, messages);
        return Utils.uncheck(() -> {
            BotApiResponse response = lineMessagingClient.pushMessage(new PushMessage(userId, messages)).get();
            log.info("pushMessage result. {}", response);
            return response;
        });
    }
}
