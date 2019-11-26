package com.linebot.repository;

import com.linebot.entity.Tobuy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TobuyRepository extends JpaRepository<Tobuy, Long> {
    List<Tobuy> findByUserIdAndIsCompleted(String userId, String isCompleted);
    Tobuy findByIdAndUserIdAndGoodsAndIsCompleted(Integer id, String userId, String goods, String isCompleted);

    @Modifying
    @Query(
            value = "update tobuy set is_completed = '1', updated_date = current_timestamp where user_id = ${userId} and is_completed = '0';",
            nativeQuery = true
    )
    int updateAllCompleted(@Param("userId") String userId);
}