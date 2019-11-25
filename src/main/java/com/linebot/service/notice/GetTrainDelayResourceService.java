package com.linebot.service.notice;

import com.linebot.client.train.TrainDelayClient;
import com.linebot.entity.BotUser;
import com.linebot.model.train.TrainDelay;
import com.linebot.service.message.PushMessageService;
import com.linebot.service.user.BotUserService;
import com.linebot.util.Utils;
import com.linecorp.bot.model.message.TextMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class GetTrainDelayResourceService {
    private PushMessageService pushMessageService;
    private TrainDelayClient trainDelayClient;
    private BotUserService botUserService;

    public void execute() {
        List<TrainDelay> results = trainDelayClient.getDelay().stream()
                .filter(t -> t.getCompany().equals("JR東日本"))
                .collect(Collectors.toList());
        StringBuilder sb = new StringBuilder(128);
        if (results.isEmpty()) {
            sb.append("遅延している沿線はありません。");
        } else {
            sb.append("遅延している沿線は以下です。");
            results.stream()
                    .filter(r -> !StringUtils.isEmpty(r))
                    .forEach(r -> sb.append(Utils.LINE_SEPARATOR).append(r.getName()));
        }
        Set<String> userIds = botUserService.findActiveUser().stream()
                .map(BotUser::getUserId)
                .collect(Collectors.toSet());
        pushMessageService.multicast(userIds, Collections.singletonList(new TextMessage(sb.toString())));
    }
}
