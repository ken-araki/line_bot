package com.linebot.controller.test;

import com.linebot.client.qiita.QiitaClient;
import com.linebot.model.qiita.Item;
import com.linebot.model.qiita.ItemSummary;
import com.linebot.model.qiita.QiitaRequestParameter;
import com.linebot.service.qiita.QiitaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(path = "/test/qiita")
@Profile("local")
public class QiitaController {
    private QiitaClient client;
    private QiitaService qiitaService;

    @GetMapping("/items")
    public List<Item> get() {
        QiitaRequestParameter p = QiitaRequestParameter.builder()
                .page(1)
                .perPage(100)
                .build();
        return client.getUserItem("kaad", p);
    }
    @GetMapping("/itemSummary")
    public List<ItemSummary> getItemSummary() {
        QiitaRequestParameter p = QiitaRequestParameter.builder()
                .page(1)
                .perPage(100)
                .build();
        return qiitaService.getItemByUserId("kaad", p);
    }
}
