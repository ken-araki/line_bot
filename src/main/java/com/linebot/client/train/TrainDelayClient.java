package com.linebot.client.train;

import com.linebot.client.UriBuilder;
import com.linebot.client.WebClient;
import com.linebot.model.train.TrainDelay;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
public class TrainDelayClient {
    private WebClient webClient;

    /**
     * 鉄道comのRSSを集計して遅延している電車を返すAPIを実行する。
     * 電車遅延情報を取得する。
     * 参照URL: https://rti-giken.jp/fhc/api/train_tetsudo/
     *
     * @return 電車遅延情報リスト
     */
    public List<TrainDelay> getDelay() {
        String url = UriBuilder.TRAIN_DELAY.build();
        return Arrays.asList(webClient.get(url, TrainDelay[].class));
    }
}
