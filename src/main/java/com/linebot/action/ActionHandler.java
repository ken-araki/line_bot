package com.linebot.action;

import com.linebot.message.FlexMessageBuilder;
import com.linebot.model.UserStatus;
import com.linebot.service.UserStatusCacheService;
import com.linebot.service.log.LogService;
import com.linebot.service.notice.NoticeService;
import com.linebot.service.user.BotUserQiitaService;
import com.linebot.service.user.BotUserService;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
public class ActionHandler {
    private ApplicationContext applicationContext;
    private UserStatusCacheService userStatusCacheService;
    private BotUserService botUserService;
    private BotUserQiitaService botUserQiitaService;
    private NoticeService noticeService;
    private FlexMessageBuilder flexMessageBuilder;
    private LogService logService;

    public List<Message> follow(@NotNull String userId) {
        botUserService.insert(userId);
        return Arrays.asList(
                new TextMessage("ユーザ登録を行いました。以下操作が実行可能です。"),
                flexMessageBuilder.buildStartWordMessage()
        );
    }

    @Transactional
    public void unfollow(@NotNull String userId) {
        botUserService.delete(userId);
        botUserQiitaService.delete(userId);
        noticeService.deleteNotice(userId);
    }

    @NotNull
    public List<Message> handle(@NotNull String userId, @NotNull String message) {
        ActionSelector actionSelector = ActionSelector.getByStartWord(message);
        if (actionSelector != null) {
            return executeStartAction(actionSelector, userId, message);
        }

        UserStatus status = this.getStatus(userId);
        if (status.getNextAction() == null) {
            return Arrays.asList(
                    new TextMessage("このメッセージは受け付けられません。どの操作を実行するか選択してください"),
                    flexMessageBuilder.buildStartWordMessage()
            );
        }

        Action action = getAction(status.getNextAction());
        if (action.check(message)) {
            List<Message> result = execute(action, userId, message);
            userStatusCacheService.set(userId, createUserStatus(userId, action.getNextAction()));
            return result;
        } else {
            userStatusCacheService.delete(userId);
            return Arrays.asList(
                    new TextMessage("このメッセージは受け付けられません。どの操作を実行するか選択してください"),
                    flexMessageBuilder.buildStartWordMessage()
            );
        }
    }

    private List<Message> execute(@NotNull Action action, @NotNull String userId, @NotNull String message) {
        logService.insertBotLog(userId, action.getClass().getSimpleName(), message);
        return action.execute(userId, message);
    }

    private List<Message> executeStartAction(ActionSelector actionSelector, String userId, String message) {
        Action action = getStartAction(actionSelector);
        // スタートワードなのでチェック不要でexecute実行する
        List<Message> result = execute(action, userId, message);
        userStatusCacheService.set(userId, createUserStatus(userId, action.getNextAction()));
        return result;
    }

    private Action getStartAction(ActionSelector actionSelector) {
        String actionName = actionSelector.getActionList().stream()
                .findFirst()
                .get();
        return getAction(actionName);
    }

    private Action getAction(String actionName) {
        return (Action) applicationContext.getBean(actionName);
    }

    private UserStatus getStatus(String userId) {
        UserStatus u = userStatusCacheService.get(userId);
        return u != null ? u : new UserStatus();
    }

    private UserStatus createUserStatus(@NotNull String userId, @Nullable String nextAction) {
        UserStatus u = new UserStatus();
        u.setUserId(userId);
        u.setNextAction(nextAction);
        return u;
    }
}
