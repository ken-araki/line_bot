package com.linebot.repository;

import com.linebot.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findByTypeAndDeleted(int type, String deleted);
    List<Notice> findByUserIdAndDeleted(String userId, String deleted);
    List<Notice> findByUserIdAndType(String userId, int type);
}
