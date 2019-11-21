package com.linebot.service.qiita;

import com.linebot.client.qiita.QiitaClient;
import com.linebot.model.qiita.ItemSummary;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class QiitaService {
    private QiitaClient qiitaClient;

    public List<ItemSummary> getItemByUserId(String userId) {
        return qiitaClient.getUserItem(userId).stream()
                .map(i -> {
                    int stock = qiitaClient.getItemStocker(i.getId()).size();
                    ItemSummary itemSummary = new ItemSummary();
                    itemSummary.setId(i.getId());
                    itemSummary.setTitle(i.getTitle());
                    itemSummary.setUrl(i.getUrl());
                    itemSummary.setLikesCount(i.getLikesCount());
                    itemSummary.setPageViewsCount(i.getPageViewsCount());
                    itemSummary.setStockersCount(stock);
                    return itemSummary;
                }).sorted(Comparator
                        .comparing(ItemSummary::getLikesCount, Comparator.reverseOrder())
                        .thenComparing(ItemSummary::getStockersCount, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }
}
