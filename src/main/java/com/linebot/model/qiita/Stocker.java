package com.linebot.model.qiita;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stocker {
    // https://qiita.com/api/v2/docs#get-apiv2itemsitem_idstockers

    private String description;
    private String id;
    private String name;
    private String organization;
    @JsonProperty("profile_image_url")
    private String profileImageUrl;
    @JsonProperty("website_url")
    private String websiteUrl;
}
