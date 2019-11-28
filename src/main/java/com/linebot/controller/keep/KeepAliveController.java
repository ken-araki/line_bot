package com.linebot.controller.keep;

import com.linebot.model.response.RestApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(path = "/self")
public class KeepAliveController {
    @GetMapping("/keep")
    public RestApiResponse index() {
        log.info("keep alive.");
        return RestApiResponse.builder()
                .status("success")
                .build();
    }
}
