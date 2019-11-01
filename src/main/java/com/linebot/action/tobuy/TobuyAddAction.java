package com.linebot.action.tobuy;

import com.linebot.action.Action;
import com.linebot.service.TobuyService;
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
public class TobuyAddAction implements Action {
    private TobuyService tobuyService;

    @Override
    public List<Message> execute(@NotNull String userId, @NotNull String message) {
        log.info("tobuy/add/{}/", userId);
        String[] lines = message.split(Utils.LINE_SEPARATOR);
        int resultInsert = Arrays.stream(lines)
                .skip(1)
                .mapToInt(tobuyService::insertByGoods)
                .sum();

        String messageInsert = String.format("%d 件の買い物リストを登録しました.", resultInsert);
        return Arrays.asList(new TextMessage(messageInsert));
    }
}
