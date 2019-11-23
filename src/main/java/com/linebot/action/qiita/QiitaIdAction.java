package com.linebot.action.qiita;

import com.linebot.action.Action;
import com.linebot.entity.BotUserQiita;
import com.linebot.service.user.BotUserQiitaService;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
public class QiitaIdAction extends Action {
    private BotUserQiitaService botUserQiitaService;

    @Override
    public @NotNull List<Message> execute(@NotNull String userId, @NotNull String message) {
        BotUserQiita user = botUserQiitaService.updateOrCreate(userId, message);
        String replyMessage = String.format("Qiita名【%s】で設定しました", user.getQiitaUserId());
        return Collections.singletonList(
                new TextMessage(replyMessage)
        );
    }
}
