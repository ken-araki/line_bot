package com.linebot.client.train;

import com.linebot.client.WebClient;
import com.linebot.model.train.TrainDelay;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
public class TrainDelayClient {
    private WebClient webClient;

    public List<TrainDelay> getDelay() {
        return webClient.get("https://tetsudo.rti-giken.jp/free/delay.json");
    }
}
