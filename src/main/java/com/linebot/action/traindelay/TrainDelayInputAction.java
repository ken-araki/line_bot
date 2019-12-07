package com.linebot.action.traindelay;

import com.linebot.action.Action;
import com.linebot.message.TrainDelayTimeMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
public class TrainDelayInputAction extends Action {
    @NotNull
    @Override
    public List<Message> execute(@NotNull String userId, @NotNull String message) {
        String alt = "電車遅延通知する時間を選択してください";
        return Arrays.asList(
                new TextMessage(alt),
                new TrainDelayTimeMessage().build(alt)
        );
    }

    @NotNull
    @Override
    public String getNextAction() {
        return "trainDelayAction";
    }

}
