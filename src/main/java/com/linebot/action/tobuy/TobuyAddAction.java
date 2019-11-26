package com.linebot.action.tobuy;

import com.linebot.action.Action;
import com.linebot.service.tobuy.TobuyService;
import com.linebot.util.Utils;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
public class TobuyAddAction extends Action {
    private TobuyService tobuyService;

    @Transactional
    @NotNull
    @Override
    public List<Message> execute(@NotNull String userId, @NotNull String message) {
        String[] lines = message.split(Utils.LINE_SEPARATOR);
        int resultInsert = Arrays.stream(lines)
                .filter(l -> !StringUtils.isEmpty(l))
                .mapToInt(l -> tobuyService.insertByGoods(userId, l))
                .sum();

        String messageInsert = String.format("%d 件の買い物リストを登録しました.", resultInsert);
        String messageAdd = "他にあればそのまま記載してください";
        return Arrays.asList(
                new TextMessage(messageInsert),
                new TextMessage(messageAdd)
        );
    }

    @Nullable
    @Override
    public String getNextAction() {
        return "tobuyAddAction";
    }
}
