package com.linebot.controller.test;

import com.linebot.action.ActionHandler;
import com.linebot.util.Utils;
import com.linecorp.bot.model.message.Message;
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
@RequestMapping(path = "/test/action")
@Profile("local")
public class TobuyActionController {
    private ActionHandler action;

    @GetMapping(path = "/tobuy/addstart")
    public List<Message> addstart() {
        return action.handle("1", "買い物リスト追加");
    }

    @GetMapping(path = "/tobuy/add")
    public List<Message> add(
            @RequestParam(name = "goods", required = false, defaultValue = "test") String goods
    ) {
        return action.handle("1", goods);
    }

    @GetMapping(path = "/tobuy/add2")
    public List<Message> add2(
            @RequestParam(name = "goods1", required = false, defaultValue = "test1") String goods1,
            @RequestParam(name = "goods2", required = false, defaultValue = "test2") String goods2
    ) {
        String message = goods1 + Utils.LINE_SEPARATOR + goods2;
        return action.handle("1", message);
    }

    @GetMapping(path = "/tobuy/confirm")
    public List<Message> confirm() {
        return action.handle("1", "買い物リスト確認");
    }

    @GetMapping(path = "/tobuy/complate")
    public List<Message> complate() {
        return action.handle("1", "買い物リスト購入");
    }

    @GetMapping(path = "/tobuy/complate2")
    public List<Message> complate2(
            @RequestParam(name = "id") String id,
            @RequestParam(name = "goods") String goods
    ) {
        return action.handle("1", id + " " + goods);
    }
}
