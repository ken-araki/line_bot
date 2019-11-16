package com.linebot.service.notice;

import com.linebot.util.Utils;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.Broadcast;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.function.BiFunction;

@Slf4j
@AllArgsConstructor
@Service
public class NoticeGarbageOutService {
    private LineMessagingClient lineMessagingClient;

    public void executeDayBefore() {
        if (this.isTommorowFirstOrThirdFriday()) {
            return;
        }
        final BotApiResponse response = Utils.uncheck(() -> {
            return lineMessagingClient.broadcast(new Broadcast(Collections.singletonList(
                    new TextMessage("明日は資源ごみの日です。")), false)).get();
        });
    }

    public boolean checkDay(int add, BiFunction<Integer, DayOfWeek, Boolean> fn) {
        LocalDateTime target = LocalDateTime.now(ZoneId.of("Asia/Tokyo")).plusDays(add);
        DayOfWeek dayOfWeek = target.getDayOfWeek();
        int day = target.getDayOfMonth();
        return (Boolean) fn.apply(day, dayOfWeek);
    }

    /**
     * ゴミ出し前日であるかチェックする
     * Spring-Scheduleで木曜日チェック済みだが、別ルートで叩くことも考慮しここでもチェックする
     */
    public boolean isTommorowFirstOrThirdFriday() {
        return checkDay(1, (day, dayOfWeek) -> {
                    return DayOfWeek.THURSDAY == dayOfWeek && ((1 <= day && day <= 7) || (15 <= day && day <= 21));
                }
        );
    }

}
