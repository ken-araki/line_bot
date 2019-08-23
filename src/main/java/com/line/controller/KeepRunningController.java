package com.line.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.line.config.LineProperties;

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

	/** resource loader */
	@Autowired
	ResourceLoader resourceLoader;

	/** 設定値 */
	private final LineProperties lineProperties;

	/**
	 * コンストラクタ
	 * 
	 * @param lineProperties 設定ファイル
	 */
	KeepRunningController(LineProperties lineProperties) {
		this.lineProperties = lineProperties;
	}

	/**
	 * heroku を起動し続ける
	 * 15min でsleep状態になるため、10分ごとに実行する
	 */
	@Scheduled(cron = "0 */10 * * * *")
	public void execute() {
		String url = String.format("http://%s:%s/keepRunning", lineProperties.getHost(), lineProperties.getPort());
		Resource resource = resourceLoader.getResource(url);
		try (InputStream in = resource.getInputStream()) {
			String content = StreamUtils.copyToString(in, StandardCharsets.UTF_8);
			log.info("keepRunning exec. result: {}", content);
		} catch(IOException e) {
			String message = String.format("resource access error. url:[%s]", url);
			log.error(message);
			throw new RuntimeException(message);
		}
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
