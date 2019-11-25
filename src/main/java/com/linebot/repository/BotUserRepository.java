package com.linebot.repository;

import com.linebot.entity.BotUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BotUserRepository extends JpaRepository<BotUser, Long> {
    List<BotUser> findByDeleted(String deleted);
    BotUser findByUserId(String userId);
}
