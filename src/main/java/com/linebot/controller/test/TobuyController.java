package com.linebot.controller.test;

import com.linebot.action.ManageTobuyAction;
import com.linebot.entity.Tobuy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(name = "/test/tobuy")
public class TobuyController {
    private ManageTobuyAction manageTobuyAction;

    @GetMapping(path = "/insert")
    public int insert(
            @RequestParam(name = "goods", required = false, defaultValue = "test") String goods
    ) {
        manageTobuyAction.insertByGoods(goods);
        return 1;
    }

    @GetMapping(path = "/find")
    public List<Tobuy> find() {
        return manageTobuyAction.findByIsCompleted("0");
    }

    @GetMapping(path = "/update")
    public int update() {
        return manageTobuyAction.updateAllCompleted();
    }
}
