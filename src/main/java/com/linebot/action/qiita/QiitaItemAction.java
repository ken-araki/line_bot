package com.linebot.action.qiita;

import com.linebot.action.Action;
import com.linebot.entity.BotUserQiita;
import com.linebot.message.QiitaItemMessage;
import com.linebot.model.qiita.ItemSummary;
import com.linebot.model.qiita.QiitaRequestParameter;
import com.linebot.service.qiita.QiitaService;
import com.linebot.service.user.BotUserQiitaService;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
public class QiitaItemAction extends Action {
    private BotUserQiitaService botUserQiitaService;
    private QiitaService qiitaService;
    private QiitaItemMessage qiitaItemMessage;

    @Override
    public @NotNull List<Message> execute(@NotNull String userId, @NotNull String message) {
        BotUserQiita user = botUserQiitaService.findByUserId(userId);
        if (user == null || StringUtils.isEmpty(user.getQiitaUserId())) {
            String replyMessage = "Qiita名を設定してください";
            return Collections.singletonList(
                    new TextMessage(replyMessage)
            );
        }
        List<ItemSummary> list = qiitaService.getItemByUserId(
                user.getQiitaUserId(),
                QiitaRequestParameter.builder()
                        .perPage(20)
                        .page(1)
                        .build()
        );
        return Collections.singletonList(
                qiitaItemMessage.build(user.getQiitaUserId(), list)
        );
    }
}
