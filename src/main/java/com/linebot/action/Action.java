package com.linebot.action;

import com.linecorp.bot.model.message.Message;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.List;

public abstract class Action {
    @NotNull
    public abstract List<Message> execute(@NotNull String userId, @NotNull String message);

    public boolean check(@NotNull String message) {
        return true;
    }

    @Nullable
    public String getNextAction() {
        return null;
    }
}
