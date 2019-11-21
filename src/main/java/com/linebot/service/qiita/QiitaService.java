package com.linebot.service.qiita;

import com.linebot.client.qiita.QiitaClient;
import com.linebot.model.common.RequestParameter;
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

    public List<ItemSummary> getItemByUserId(String userId, RequestParameter p) {
        return qiitaClient.getUserItem(userId, p).stream()
                .map(i -> {
                    int stock = qiitaClient.getItemStocker(i.getId(), p).size();
                    return ItemSummary.builder()
                            .id(i.getId())
                            .title(i.getTitle())
                            .url(i.getUrl())
                            .likesCount(i.getLikesCount())
                            .stockersCount(stock)
                            .pageViewsCount(i.getPageViewsCount())
                            .build();
                }).sorted(Comparator
                        .comparing(ItemSummary::getLikesCount, Comparator.reverseOrder())
                        .thenComparing(ItemSummary::getStockersCount, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }
}
