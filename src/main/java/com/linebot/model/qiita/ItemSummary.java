package com.linebot.model.qiita;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemSummary {
    private String id;
    private String title;
    private String url;
    private int likesCount;
    private int pageViewsCount;
    private int stockersCount;
}
