package com.linebot.action.order;

import com.linebot.action.Action;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
public class OrderInputAction extends Action {
    @NotNull
    @Override
    public List<Message> execute(@NotNull String userId, @NotNull String message) {
        return Collections.singletonList(
                new TextMessage("要望・バグを記入してください")
        );
    }

    @Nullable
    @Override
    public String getNextAction() {
        return "orderAction";
    }
}
