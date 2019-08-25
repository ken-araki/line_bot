package com.line.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.line.service.GetSelfResourceService;

import lombok.extern.slf4j.Slf4j;

/**
 * herokuを起動し続けるようにする
 * 
 * @author arakikenji
 */
@Slf4j
@RestController
public class KeepRunningController {
	/** ログ */
	private static final Logger log = LoggerFactory.getLogger(KeepRunningController.class);

	/** 設定値 */
	private final GetSelfResourceService getSelfResourceService;

	/**
	 * コンストラクタ
	 * 
	 * @param lineProperties 設定ファイル
	 */
	KeepRunningController(GetSelfResourceService getSelfResourceService) {
		this.getSelfResourceService = getSelfResourceService;
	}

	/**
	 * heroku を起動し続ける
	 * 15min でsleep状態になるため、10分ごとに実行する
	 */
	@Scheduled(cron = "0 */10 * * * *")
	public void execute() {
		String content = getSelfResourceService.getKeepRunning();
		log.info("keepRunning exec. result: {}", content);
	}

	/**
	 * keepRunning アクセス用
	 * @return "keepRunning"という文字列
	 */
	@RequestMapping(
			value="/keepRunning",
			method=RequestMethod.GET
	)
	@ResponseBody
	public String index() {
		return "keepRunning";
	}
}
