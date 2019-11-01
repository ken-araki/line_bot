package com.linebot.action.tobuy;

import com.linebot.action.Action;
import com.linebot.service.TobuyService;
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
public class TobuyComplateAction implements Action {
    private TobuyService tobuyService;

    @Override
    public List<Message> execute(@NotNull String userId, @NotNull String message) {
        log.info("tobuy/complate/{}/", userId);
        int result = tobuyService.findByIsCompleted("0").stream()
                .mapToInt(t -> tobuyService.updateCompleted(t))
                .sum();
        String messageUpdate = "登録されていた" + result + "件の商品を購入しました";
        return Arrays.asList(new TextMessage(messageUpdate));
    }
}
