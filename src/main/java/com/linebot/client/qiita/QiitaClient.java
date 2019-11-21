package com.linebot.client.qiita;

import com.linebot.client.UriBuilder;
import com.linebot.client.WebClient;
import com.linebot.model.common.RequestParameter;
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

    public List<Item> getUserItem(String userId, RequestParameter p) {
        String url = UriBuilder.QIITA_USER_ITEMS.build(p, userId);
        return Arrays.asList(webClient.get(url, Item[].class));
    }

    public List<Stocker> getItemStocker(String itemId, RequestParameter p) {
        String url = UriBuilder.QIITA_ITEM_STOCK.build(p, itemId);
        return Arrays.asList(webClient.get(url, Stocker[].class));
    }
}
