package com.linebot.client.self;

import com.linebot.client.WebClient;
import com.linebot.config.PropertiesConfig;
import com.linebot.model.response.RestApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class SelfClient {
    private WebClient webClient;
    private PropertiesConfig config;

    public RestApiResponse keepAlive() {
        String url = config.getOwnHost() + ":" + config.getOwnPort() + "/self/keep";
        return webClient.get(url, RestApiResponse.class);
    }
}
