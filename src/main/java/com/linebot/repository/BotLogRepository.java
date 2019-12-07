package com.linebot.repository;

import com.linebot.entity.BotLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BotLogRepository extends JpaRepository<BotLog, Long> {
}