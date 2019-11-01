package com.linebot.action;

import com.linecorp.bot.model.message.Message;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface Action {
    List<Message> execute(@NotNull String userId, @NotNull String message);
}
