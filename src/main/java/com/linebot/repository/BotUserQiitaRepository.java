package com.linebot.repository;

import com.linebot.entity.BotUserQiita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BotUserQiitaRepository extends JpaRepository<BotUserQiita, Long> {
    List<BotUserQiita> findByDeleted(String deleted);
    BotUserQiita findByUserId(String userId);
}
