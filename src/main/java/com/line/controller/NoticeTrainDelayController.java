package com.line.controller;

import java.util.StringJoiner;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.line.bean.TrainDelay;
import com.line.config.LineProperties;
import com.line.service.GetTrainDelayResourceService;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.response.BotApiResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 電車遅延している沿線を通知する
 * 
 * @author arakikenji
 */
@Slf4j
@Controller
public class NoticeTrainDelayController {
	/** log */
	private static final Logger log = LoggerFactory.getLogger(NoticeTrainDelayController.class);

	/** アプリ設定情報 */
	private final LineProperties lineProperties;
	/** LINE bot接続クライアント */
	private final LineMessagingClient lineMessagingClient;

	/** 電車遅延取得サービス */
	@Autowired
	GetTrainDelayResourceService getTrainDelayResourceService;

	/**
	 * コンストラクタ
	 * 
	 * @param lineProperties アプリ設定情報
	 * @param lineMessagingClient LINE bot接続クライアント
	 */
	NoticeTrainDelayController(LineProperties lineProperties, LineMessagingClient lineMessagingClient) {
		this.lineProperties = lineProperties;
		this.lineMessagingClient = lineMessagingClient;
	}

	/**
	 * JR東日本の電車遅延情報を通知する
	 */
	@Scheduled(cron = "0 */5 * * * *", zone = "Asia/Tokyo")
	public void executeJrEast() {
		String result = null;
		TrainDelay[] list = getTrainDelayResourceService.getDelayJrEast();
		if (list.length > 0) {
			StringJoiner sj = new StringJoiner(",");
			Stream.of(list).forEach(l -> sj.add(l.getName()));
			result = String.format("電車遅延している沿線は、%s", sj.toString());
		} else {
			result = "遅延している沿線はありません。";
		}

		try {
			log.info("exec executeJrEast()");
			final BotApiResponse response = lineMessagingClient.pushMessage(
					new PushMessage(lineProperties.getId(), new TemplateMessage(result, null))
			).get();
			log.info("Sent messages: {}", response);
		} catch (InterruptedException | ExecutionException e) {
			log.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}
}
