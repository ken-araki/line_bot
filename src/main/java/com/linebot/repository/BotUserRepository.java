package com.linebot.repository;

import com.linebot.entity.BotUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BotUserRepository extends JpaRepository<BotUser, Long> {
    // 将来、削除処理も実装するため、今は全件だけどアクティブユーザのみ取得する
    List<BotUser> findByDeleted(String deleted);
}
