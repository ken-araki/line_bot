package com.linebot.batch;

import com.linebot.service.NoticeGarbageOutService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Slf4j
@AllArgsConstructor
@Controller
public class NoticeGarbageOutBatch {
	private NoticeGarbageOutService noticeGarbageOutService;

	@Scheduled(cron = "0 20 21 * * 4", zone = "Asia/Tokyo")
	public void execute() {
		noticeGarbageOutService.executeDayBefore();
	}
}
