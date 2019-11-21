package com.linebot.model.qiita;

import com.linebot.model.common.RequestParameter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QiitaRequestParameter implements RequestParameter {
    private Integer page;
    private Integer perPage;

    @Override
    public MultiValueMap<String, String> convert() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        add(params, "page", this.page);
        add(params, "per_page", this.perPage);
        return params;
    }
}
