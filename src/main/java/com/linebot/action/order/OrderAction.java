package com.linebot.action.order;

import com.linebot.action.Action;
import com.linebot.entity.Request;
import com.linebot.service.request.RequestService;
import com.linebot.util.Utils;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
public class OrderAction extends Action {
    private RequestService requestService;
    @NotNull
    @Override
    public List<Message> execute(@NotNull String userId, @NotNull String message) {
        Request request = Request.builder()
                .userId(userId)
                .request(message)
                .status(0)
                .deleted("0")
                .createdAt(Utils.now())
                .build();
        requestService.insert(request);
        return Collections.singletonList(
                new TextMessage("ご意見ありがとうございます！")
        );
    }
}
