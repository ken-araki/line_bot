package com.linebot.action;

import com.linebot.entity.BotUser;
import com.linebot.message.FlexMessageBuilder;
import com.linebot.model.UserStatus;
import com.linebot.service.UserStatusCacheService;
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
import java.util.Collections;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
public class ActionHandler {
    private ApplicationContext applicationContext;
    private UserStatusCacheService userStatusCacheService;
    private BotUserService botUserService;
    private BotUserQiitaService botUserQiitaService;
    private FlexMessageBuilder flexMessageBuilder;

    public List<Message> follow(@NotNull String userId) {
        // フリープランの場合、メッセージは月1,000通となっているため、
        // 機能を提供するユーザは30とする。
        // 何かいい方法を見つけたなら消す
        List<BotUser> users = botUserService.findActiveUser();
        if (users.size() >= 30) {
            return Collections.singletonList(
                    new TextMessage("ユーザ登録数が上限に達しています。利用可能までしばしお待ちください。")
            );
        }
        // ここまで
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
    }

    @Nullable
    public List<Message> handle(@NotNull String userId, @NotNull String message) {
        // ここの部分もユーザ上限によるもの
        // メッセージを無駄打ちしないように、Nullを返す
        // ここを消したなら@Nullable -> @NotNull へ戻す
        BotUser user = botUserService.findActiveUserByUserId(userId);
        if (user == null || !"0".equals(user.getDeleted())) {
            return null;
        }
        // ここまで
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
            List<Message> result = action.execute(userId, message);
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

    private List<Message> executeStartAction(ActionSelector actionSelector, String userId, String message) {
        Action action = getStartAction(actionSelector);
        // スタートワードなのでチェック不要でexecute実行する
        List<Message> result = action.execute(userId, message);
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
