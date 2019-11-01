package com.linebot.action;

import com.linebot.util.Utils;
import com.linecorp.bot.model.message.Message;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
public class ActionHandler {
    private ApplicationContext applicationContext;

    public List<Message> handle(@NotNull String userId, @NotNull String message) {
        String operation = message.split(Utils.LINE_SEPARATOR)[0];
        Action target = this.getAction(operation);
        return target.execute(userId, message);
    }

    public Action getAction(String operation) {
        for (ActionMapping mapping : ActionMapping.values()) {
            if (mapping.operation.equals(operation)) {
                return (Action)applicationContext.getBean(mapping.executor);
            }
        }
        log.error("Nothing Aciton with operation. [{}]", operation);
        throw new IllegalArgumentException("Nothing Aciton");
    }
    @AllArgsConstructor
    public enum ActionMapping {
        TOBUY_CONFIRM("買い物リスト確認", "TobuyConfirmAction"),
        TOBUY_ADD("買い物リスト追加", "TobuyAddAction"),
        TOBUY_COMPLATE("買い物リスト購入", "TobuyComplateAction");

        private String operation;
        private String executor;
    }
}
