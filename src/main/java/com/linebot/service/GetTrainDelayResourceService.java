package com.linebot.service;

import java.time.Duration;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import com.linebot.entry.TrainDelay;

@Slf4j
@Service
public class GetTrainDelayResourceService {
	private RestOperations restOperations;
	
	public GetTrainDelayResourceService(RestTemplateBuilder restTemplateBuilder) {
		String url = "https://tetsudo.rti-giken.jp";
		this.restOperations = restTemplateBuilder
				.rootUri(url)
				.setConnectTimeout(Duration.ofSeconds(2))
				.setReadTimeout(Duration.ofSeconds(1))
				.build();
	}
	
	/**
	 * 鉄道comのRSSを集計して遅延している電車を返すAPIを実行する。
	 * 電車遅延情報を取得する。
	 * 参照URL: https://rti-giken.jp/fhc/api/train_tetsudo/
	 * 
	 * @return 電車遅延情報リスト
	 */
	public TrainDelay[] getDelay() {
		return restOperations.getForObject("/free/delay.json", TrainDelay[].class);
	}
	
	/**
	 * 鉄道comのRSSを集計して遅延している電車を返すAPIを実行する。
	 * その中から「JR東日本」のみを取得する
	 * 参照URL: https://rti-giken.jp/fhc/api/train_tetsudo/
	 * 
	 * @return JR東日本の電車遅延情報リスト
	 */
	public TrainDelay[] getDelayJrEast() {
		TrainDelay[] list = this.getDelay();
		return Stream.of(list)
				.filter(l -> l.getCompany().equals("JR東日本"))
				.toArray(TrainDelay[]::new);
	}
}
