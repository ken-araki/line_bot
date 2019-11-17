package com.linebot.controller.test;

import com.linebot.client.train.TrainDelayClient;
import com.linebot.model.train.TrainDelay;
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
@RequestMapping(path = "/test/train")
@Profile("local")
public class TrainDelayController {
    private TrainDelayClient client;
    @GetMapping("/delay")
    public List<TrainDelay> get() {
        return client.getDelay();
    }
}
