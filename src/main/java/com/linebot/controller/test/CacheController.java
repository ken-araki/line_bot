package com.linebot.controller.test;

import com.linebot.model.UserStatus;
import com.linebot.service.UserStatusCacheService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(path = "/test/cache")
@Profile("local")
public class CacheController {
    private UserStatusCacheService service;
    @GetMapping(path = "/set")
    public String set(
            @RequestParam(name = "userId", required = false, defaultValue = "1") String userId,
            @RequestParam(name = "nextAction", required = false, defaultValue = "nextAction") String nextAction
    ) {
        UserStatus userStatus = new UserStatus();
        userStatus.setUserId(userId);
        userStatus.setNextAction(nextAction);
        service.set(userId, userStatus);
        return "Succes";
    }
    @GetMapping(path = "/get")
    public UserStatus get(
            @RequestParam(name = "id", required = false, defaultValue = "1") String userId
    ) {
        return service.get(userId);
    }


}
