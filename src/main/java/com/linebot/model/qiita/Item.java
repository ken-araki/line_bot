package com.linebot.model.qiita;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    // https://qiita.com/api/v2/docs#get-apiv2usersuser_iditems
    // いろんなデータがあるが、いったん欲しい情報だけ抽出する
    // 記載順序も変えている

    private String id;
    private String title;
    private String url;

    private boolean coediting;
    @JsonProperty("comments_count")
    private int commentsCount;
    @JsonProperty("likes_count")
    private int likesCount;
    @JsonProperty("page_views_count")
    private int pageViewsCount;
    @JsonProperty("reactions_count")
    private int reactionsCount;

    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("updated_at")
    private Date updatedAt;
}
