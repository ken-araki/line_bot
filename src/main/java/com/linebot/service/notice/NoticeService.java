package com.linebot.service.notice;

import com.linebot.action.traindelay.TrainDelayTime;
import com.linebot.entity.Notice;
import com.linebot.entity.Notice.Type;
import com.linebot.repository.NoticeRepository;
import com.linebot.util.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class NoticeService {
    private NoticeRepository noticeRepository;

    @Transactional
    public Notice insertOrUpdateTrainDelay(String userId, TrainDelayTime time) {
        List<Notice> notices = noticeRepository.findByUserIdAndType(userId, Type.TRAIN_DELAY.getCode());
        Notice notice = null;
        if (notices == null || notices.isEmpty()) {
            notice = Notice.builder()
                    .userId(userId)
                    .year(0)
                    .month(0)
                    .day(0)
                    .dayOfWeek(0)
                    .minute(time.getMinute())
                    .type(Notice.Type.TRAIN_DELAY.getCode())
                    .deleted("0")
                    .createdAt(Utils.now())
                    .build();
        } else {
            // 電車遅延通知は1ユーザ、1回としている
            notice = notices.get(0);
        }
        notice.setHour(time.getHour());
        notice.setMinute(time.getMinute());
        return noticeRepository.save(notice);
    }

    @Transactional(readOnly = true)
    public List<Notice> findTrainDelay() {
        return noticeRepository.findByTypeAndDeleted(Type.TRAIN_DELAY.getCode(), "0");
    }

    @Transactional
    public void deleteNotice(String userId) {
        noticeRepository.findByUserIdAndDeleted(userId, "0").stream()
                .forEach(n -> {
                    n.setDeleted("1");
                    noticeRepository.save(n);
                });


    }
}
