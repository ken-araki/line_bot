package com.linebot.action.tobuy;

import com.linebot.action.Action;
import com.linebot.entity.Tobuy;
import com.linebot.service.tobuy.TobuyService;
import com.linebot.util.Utils;
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
public class TobuyConfirmAction extends Action {
    private TobuyService tobuyService;

    @NotNull
    @Override
    public List<Message> execute(@NotNull String userId, @NotNull String message) {
        log.info("tobuy/confirm/{}/", userId);
        List<Tobuy> tobuyList = tobuyService.findByIsCompleted(userId, "0");
        StringBuilder sb = new StringBuilder("買い物リストはこちらです");
        tobuyList.stream().forEach(t -> sb.append(Utils.LINE_SEPARATOR + t.getGoods()));
        return Arrays.asList(new TextMessage(sb.toString()));
    }
}
