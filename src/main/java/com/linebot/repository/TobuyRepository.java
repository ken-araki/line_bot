package com.linebot.repository;

import com.linebot.entity.Tobuy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TobuyRepository extends JpaRepository<Tobuy, Long> {
    List<Tobuy> findByIsCompleted(String isCompleted);

    @Modifying
    @Query(
            value = "update tobuy set is_completed = '1', updated_date = current_timestamp where is_completed = '0';",
            nativeQuery = true
    )
    void updateAllCompleted();
}