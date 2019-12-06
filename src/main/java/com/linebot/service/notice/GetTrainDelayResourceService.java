package com.linebot.service.notice;

import com.linebot.client.train.TrainDelayClient;
import com.linebot.model.train.TrainDelay;
import com.linebot.service.log.LogService;
import com.linebot.service.message.PushMessageService;
import com.linebot.util.Utils;
import com.linecorp.bot.model.message.TextMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class GetTrainDelayResourceService {
    private PushMessageService pushMessageService;
    private TrainDelayClient trainDelayClient;
    private NoticeService noticeService;
    private LogService logService;

    public void execute() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Tokyo"));
        int hour = now.getHour();
        int minute = now.getMinute();

        List<TrainDelay> results = trainDelayClient.getDelay();
        StringBuilder sb = new StringBuilder(128);
        if (results.isEmpty()) {
            sb.append("遅延している沿線はありません。");
        } else {
            sb.append("遅延している沿線は以下です。");
            results.stream()
                    .filter(r -> !StringUtils.isEmpty(r))
                    .forEach(r -> sb.append(Utils.LINE_SEPARATOR).append(r.getName()));
        }
        logService.insertTrainDelay(results);
        Set<String> userIds = noticeService.findTrainDelay().stream()
                .filter(n -> n.getHour() == hour)
                .filter(n -> n.getMinute() == minute)
                .map(n -> n.getUserId())
                .collect(Collectors.toSet());

        if (userIds.size() > 0) {
            pushMessageService.multicast(userIds, Collections.singletonList(new TextMessage(sb.toString())));
        }
    }
}
