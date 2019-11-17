package com.linebot.batch;

import com.linebot.model.train.TrainDelay;
import com.linebot.service.message.PushMessageService;
import com.linebot.service.notice.GetTrainDelayResourceService;
import com.linebot.util.Utils;
import com.linecorp.bot.model.message.TextMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.Collections;
import java.util.stream.Stream;

@Slf4j
@AllArgsConstructor
@Controller
public class NoticeTrainDelayBatch {
    private PushMessageService pushMessageService;
    private GetTrainDelayResourceService getTrainDelayResourceService;

    @Scheduled(cron = "0 5 8 * * *", zone = "Asia/Tokyo")
    public void executeJrEast() {
        StringBuilder sb = new StringBuilder(128);
        TrainDelay[] list = getTrainDelayResourceService.getDelay();
        if (list.length > 0) {
            sb.append("遅延している沿線は以下です。");
            Stream.of(list).forEach(l -> sb.append(Utils.LINE_SEPARATOR).append(l.getName()));
        } else {
            sb.append("遅延している沿線はありません。");
        }
        pushMessageService.broadcast(Collections.singletonList(new TextMessage(sb.toString())));
    }
}
