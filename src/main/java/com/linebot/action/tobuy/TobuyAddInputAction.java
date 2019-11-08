package com.linebot.action.tobuy;

import com.linebot.action.Action;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
public class TobuyAddInputAction extends Action {

    @NotNull
    @Override
    public List<Message> execute(@NotNull String userId, @NotNull String message) {
        String messageAdd = "改行区切りで買い物リストを登録してください";
        return Arrays.asList(
                new TextMessage(messageAdd)
        );
    }

    @Nullable
    @Override
    public String getNextAction() {
        return "tobuyAddAction";
    }
}
