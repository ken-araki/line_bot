package com.line.service;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import com.line.config.LineProperties;

@Service
public class GetSelfResourceService {
	LineProperties lineProperties;
	RestOperations restOperations;
	public GetSelfResourceService(LineProperties lineProperties, RestTemplateBuilder restTemplateBuilder) {
		this.lineProperties = lineProperties;
		String url = String.format("http://%s:%s", lineProperties.getHost(), lineProperties.getPort());
		this.restOperations = restTemplateBuilder
				.rootUri(url)
				.setConnectTimeout(Duration.ofSeconds(10))
				.setReadTimeout(Duration.ofSeconds(10))
				.build();
		
	}
	public String getKeepRunning() {
		return restOperations.getForObject("/keepRunning", String.class);
	}
}
