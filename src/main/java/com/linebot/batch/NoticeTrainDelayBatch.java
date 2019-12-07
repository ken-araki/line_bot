package com.linebot.batch;

import com.linebot.service.notice.GetTrainDelayResourceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Slf4j
@AllArgsConstructor
@Controller
public class NoticeTrainDelayBatch {
    private GetTrainDelayResourceService getTrainDelayResourceService;

    @Scheduled(cron = "0 */20 7-9 * * *", zone = "Asia/Tokyo")
    public void execute() {
        getTrainDelayResourceService.execute();
    }
}
