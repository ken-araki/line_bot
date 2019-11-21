package com.linebot.client.qiita;

import com.linebot.client.WebClient;
import com.linebot.model.qiita.Item;
import com.linebot.model.qiita.Stocker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
public class QiitaClient {
    private WebClient webClient;

    public List<Item> getUserItem(String userId) {
        return Arrays.asList(webClient.get("https://qiita.com/api/v2/users/" + userId + "/items?page=1&per_page=100", Item[].class));
    }
    public List<Stocker> getItemStocker(String itemId) {
        return Arrays.asList(webClient.get("https://qiita.com/api/v2/items/" + itemId + "/stockers?page=1&per_page=100", Stocker[].class));
    }
}
