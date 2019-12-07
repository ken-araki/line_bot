package com.linebot.service.log;

import com.linebot.entity.BotLog;
import com.linebot.entity.TrainDelayLog;
import com.linebot.model.train.TrainDelay;
import com.linebot.repository.BotLogRepository;
import com.linebot.repository.TrainDelayLogRepository;
import com.linebot.util.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class LogService {
    private BotLogRepository botLogRepository;
    private TrainDelayLogRepository trainDelayLogRepository;

    @Transactional
    @NotNull
    public BotLog insertBotLog(
            @NotNull String userId,
            @NotNull String botName,
            @NotNull String message
    ) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Tokyo"));
        String yearMonth = String.format("%d%d", now.getYear(), now.getMonthValue());
        return botLogRepository.save(
            BotLog.builder()
                    .userId(userId)
                    .botActionName(botName)
                    .message(message)
                    .yearMonth(yearMonth)
                    .day(now.getDayOfMonth())
                    .dayOfWeek(now.getDayOfWeek().getValue())
                    .hour(now.getHour())
                    .minute(now.getMinute())
                    .execTime(Utils.now())
                    .build()
        );
    }

    @Transactional
    @NotNull
    public List<TrainDelayLog> insertTrainDelay(
            @NotNull List<TrainDelay> trains
    ) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Tokyo"));
        String yearMonth = String.format("%d%d", now.getYear(), now.getMonthValue());
        List<TrainDelayLog> logs = trains.stream()
                .map(t -> {
                    return TrainDelayLog.builder()
                            .trainName(t.getName())
                            .yearMonth(yearMonth)
                            .day(now.getDayOfMonth())
                            .dayOfWeek(now.getDayOfWeek().getValue())
                            .hour(now.getHour())
                            .minute(now.getMinute())
                            .execTime(Utils.now())
                            .build();
                }).collect(Collectors.toList());
        return trainDelayLogRepository.saveAll(logs);
    }

}
