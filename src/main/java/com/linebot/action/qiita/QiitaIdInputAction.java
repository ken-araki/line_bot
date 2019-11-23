package com.linebot.action.qiita;

import com.linebot.action.Action;
import com.linebot.entity.BotUserQiita;
import com.linebot.service.user.BotUserQiitaService;
import com.linebot.util.Utils;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
public class QiitaIdInputAction extends Action {
    private BotUserQiitaService botUserQiitaService;

    @Override
    public @NotNull List<Message> execute(@NotNull String userId, @NotNull String message) {
        BotUserQiita user = botUserQiitaService.findByUserId(userId);
        StringBuilder sb = new StringBuilder();
        if (user == null || StringUtils.isEmpty(user.getQiitaUserId())) {
            sb.append("QiitaのUser名を入力してください");
        } else {
            sb.append(String.format("%sが設定されています。", user.getQiitaUserId()));
            sb.append(Utils.LINE_SEPARATOR);
            sb.append("変更する場合は、QiitaのUser名を入力してください");
        }
        return Collections.singletonList(
                new TextMessage(sb.toString())
        );
    }

    @Nullable
    @Override
    public String getNextAction() {
        return "qiitaIdAction";
    }
}
