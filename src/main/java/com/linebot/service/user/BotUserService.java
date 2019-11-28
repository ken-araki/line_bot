package com.linebot.service.user;

import com.linebot.entity.BotUser;
import com.linebot.repository.BotUserRepository;
import com.linebot.util.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class BotUserService {
    private BotUserRepository botUserRepository;

    @Transactional(readOnly = true)
    public List<BotUser> findActiveUser() {
        return botUserRepository.findByDeleted("0");
    }

    @Transactional
    public BotUser insert(@NotNull String userId) {
        BotUser u = new BotUser();
        u.setUserId(userId);
        u.setDeleted("0");
        u.setCreatedDate(Utils.now());
        return botUserRepository.save(u);
    }

    @Transactional
    public BotUser delete(@NotNull String userId) {
        BotUser user = botUserRepository.findByUserId(userId);
        if (user == null) {
            return null;
        }
        user.setDeleted("1");
        return botUserRepository.save(user);
    }
}
