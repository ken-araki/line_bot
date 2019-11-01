package com.linebot.controller.test;

import com.linebot.entity.Tobuy;
import com.linebot.service.TobuyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(path = "/test/tobuy")
@Profile("local")
public class TobuyController {
    private TobuyService tobuyService;

    @GetMapping(path = "/insert")
    public int insert(
            @RequestParam(name = "goods", required = false, defaultValue = "test") String goods
    ) {
        return tobuyService.insertByGoods(goods);
    }

    @GetMapping(path = "/find")
    public List<Tobuy> find() {
        return tobuyService.findByIsCompleted("0");
    }

    @GetMapping(path = "/update")
    public int update() {
        List<Tobuy> tobuys = tobuyService.findByIsCompleted("0");
        if (tobuys.isEmpty()) {
            return 0;
        } else if (tobuys.size() == 1) {
            Tobuy t = tobuys.get(0);
            return tobuyService.updateCompleted(t);
        } else {
            return tobuyService.updateCompleted(tobuys);
        }
    }
}
