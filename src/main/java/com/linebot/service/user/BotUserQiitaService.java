package com.linebot.service.user;

import com.linebot.entity.BotUserQiita;
import com.linebot.repository.BotUserQiitaRepository;
import com.linebot.util.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;

@Slf4j
@AllArgsConstructor
@Service
public class BotUserQiitaService {
    private BotUserQiitaRepository botUserQiitaRepository;

    @Transactional(readOnly = true)
    public BotUserQiita findByUserId(@NotNull String userId) {
        return botUserQiitaRepository.findByUserId(userId);
    }

    @NotNull
    @Transactional
    public BotUserQiita updateOrCreate(@NotNull String userId, @NotNull String qiitaId) {
        BotUserQiita user = findByUserId(userId);
        if (user == null || StringUtils.isEmpty(user.getQiitaUserId())) {
            return botUserQiitaRepository.save(BotUserQiita.builder()
                    .userId(userId)
                    .qiitaUserId(qiitaId)
                    .deleted("0")
                    .createdDate(Utils.now())
                    .build()
            );
        } else {
            user.setQiitaUserId(qiitaId);
            user.setCreatedDate(Utils.now());
            return botUserQiitaRepository.save(user);
        }
    }
}
