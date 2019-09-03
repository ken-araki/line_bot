package com.linebot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "ark.line")
public class LineProperties {
	private String id;
	private String host;
	private String port;
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHost() {
		return this.host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return this.port;
	}
	public void setPort(String port) {
		this.port = port;
	}
}