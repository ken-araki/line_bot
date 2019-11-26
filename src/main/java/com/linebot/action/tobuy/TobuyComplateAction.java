package com.linebot.action.tobuy;

import com.linebot.action.Action;
import com.linebot.entity.Tobuy;
import com.linebot.service.tobuy.TobuyService;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Component
public class TobuyComplateAction extends Action {
    private TobuyService tobuyService;

    @Transactional
    @Override
    public List<Message> execute(@NotNull String userId, @NotNull String message) {
        String[] m = message.split(" ", 2);
        int id = Integer.parseInt(m[0]);
        String goods = m[1];

        Optional<Tobuy> tobuyOpt = Optional.ofNullable(tobuyService.findByIdAndGoods(id, userId, goods));
        if (tobuyOpt.isPresent()) {
            tobuyService.updateCompleted(tobuyOpt.get());
            String result = String.format("%sを購入しました", goods);
            return Arrays.asList(
                    new TextMessage(result),
                    new TextMessage("購入した商品を選択してください")
            );
        } else {
            return Arrays.asList(
                    new TextMessage("指定した商品は買い物リストにありませんでした"),
                    new TextMessage("購入した商品を選択してください")
            );
        }
    }

    @Override
    public boolean check(@NotNull String message) {
        return message.matches("[1-9][0-9]* .+");
    }

    @Nullable
    @Override
    public String getNextAction() {
        return "tobuyComplateAction";
    }
}
