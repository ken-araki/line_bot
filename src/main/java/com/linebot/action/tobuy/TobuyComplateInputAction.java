package com.linebot.action.tobuy;

import com.google.common.collect.Lists;
import com.linebot.action.Action;
import com.linebot.entity.Tobuy;
import com.linebot.message.FlexMessageBuilder;
import com.linebot.service.tobuy.TobuyService;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.message.FlexMessage;
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
public class TobuyComplateInputAction extends Action {
    private TobuyService tobuyService;
    private FlexMessageBuilder flexMessageBuilder;

    @NotNull
    @Override
    public List<Message> execute(@NotNull String userId, @NotNull String message) {
        List<Tobuy> tobuyList = tobuyService.findByIsCompleted(userId, "0");
        if (tobuyList.isEmpty()) {
            return Arrays.asList(new TextMessage("買い物リストはありません"));
        }
        List<MessageAction> list = Lists.newArrayList();
        tobuyList.stream()
                .forEach(t -> list.add(
                        new MessageAction(
                                t.getGoods(),
                                String.format("%d %s", t.getId(), t.getGoods())
                        )
                ));
        FlexMessage flexMessage = flexMessageBuilder.buildListMessageAction("購入した商品を選択してください", list);
        return Arrays.asList(new TextMessage("購入した商品を選択してください"), flexMessage);
    }

    @Nullable
    @Override
    public String getNextAction() {
        return "tobuyComplateAction";
    }
}
