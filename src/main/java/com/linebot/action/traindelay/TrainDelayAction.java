package com.linebot.action.traindelay;

import com.linebot.action.Action;
import com.linebot.service.notice.NoticeService;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Component
public class TrainDelayAction extends Action {
    private NoticeService noticeService;
    @NotNull
    @Override
    public List<Message> execute(@NotNull String userId, @NotNull String message) {
        TrainDelayTime time = Arrays.stream(TrainDelayTime.values())
                .filter(t -> t.getTime().equals(message))
                .findFirst()
                .get();
        noticeService.insertOrUpdateTrainDelay(userId, time);
        String replyMessage = String.format("%d時%d分に電車遅延通知を設定しました", time.getHour(), time.getMinute());
        return Collections.singletonList(
                new TextMessage(replyMessage)
        );
    }

    @Override
    public boolean check(String message) {
        Set<String> set = Arrays.stream(TrainDelayTime.values())
                .map(t -> t.getTime())
                .collect(Collectors.toSet());
        return set.contains(message);
    }
}
