package com.linebot.service.notice;

import com.linebot.entity.BotUser;
import com.linebot.service.message.PushMessageService;
import com.linebot.service.user.BotUserService;
import com.linecorp.bot.model.message.TextMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class NoticeGarbageOutService {
    private PushMessageService pushMessageService;
    private BotUserService botUserService;

    public void executeDayBefore() {
        if (this.isTommorowFirstOrThirdFriday()) {
            Set<String> userIds = botUserService.findActiveUser().stream()
                    .map(BotUser::getUserId)
                    .collect(Collectors.toSet());
            pushMessageService.multicast(userIds, Collections.singletonList(new TextMessage("明日は資源ごみの日です。")));
        }
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
                    // あしたが、第1, 第3金曜日の場合はTrueを返す
                    return DayOfWeek.FRIDAY == dayOfWeek && ((1 <= day && day <= 7) || (15 <= day && day <= 21));
                }
        );
    }

}
