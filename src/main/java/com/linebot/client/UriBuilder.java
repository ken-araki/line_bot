package com.linebot.client;

import com.linebot.model.common.RequestParameter;
import lombok.AllArgsConstructor;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
public enum UriBuilder {
    QIITA_USER_ITEMS("https://qiita.com/api/v2/users/{userId}/items"),
    QIITA_ITEM_STOCK("https://qiita.com/api/v2/items/{itemId}/stockers"),
    TRAIN_DELAY("https://tetsudo.rti-giken.jp/free/delay.json");

    private String path;

    public String build(@NotNull RequestParameter p, @NotNull String pathvalue) {
        return UriComponentsBuilder
                .fromHttpUrl(path)
                .replaceQueryParams(p.convert())
                .buildAndExpand(pathvalue)
                .toUriString();
    }

    public String build(String pathvalue) {
        return UriComponentsBuilder
                .fromHttpUrl(path)
                .buildAndExpand(pathvalue)
                .toUriString();
    }

    public String build(RequestParameter p) {
        return UriComponentsBuilder
                .fromHttpUrl(path)
                .replaceQueryParams(p.convert())
                .toUriString();
    }

    public String build() {
        return UriComponentsBuilder
                .fromHttpUrl(path)
                .toUriString();
    }
}
