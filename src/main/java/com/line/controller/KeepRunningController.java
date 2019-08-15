package com.line.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class KeepRunningController {

	private static final Logger log = LoggerFactory.getLogger(KeepRunningController.class);
	@Autowired
	HttpServletResponse response;
	@Autowired
	ResourceLoader resourceLoader;

	/**
	 * heroku を起動し続ける
	 * 15min でsleep状態になるため、10分ごとに実行する
	 */
	@Scheduled(cron = "0 */10 * * * *")
	public void execute() {
		Resource resource = resourceLoader.getResource("http://localhost/keepRunning");
		try (InputStream in = resource.getInputStream()) {
			String content = StreamUtils.copyToString(in, StandardCharsets.UTF_8);
			log.info("keepRunning exec. result: {}", content);
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	@RequestMapping(
			value="/keepRunning",
			method=RequestMethod.GET,
			produces="text/plain;charset=UTF-8"
	)
	@ResponseBody
	public String index() throws Exception {
		return "keepRunning";
	}
}
