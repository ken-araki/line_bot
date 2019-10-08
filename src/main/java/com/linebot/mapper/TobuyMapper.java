package com.linebot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.linebot.domain.Tobuy;

@Mapper
public interface TobuyMapper {
	List<Tobuy> findByIsCompleted(@Param("isCompleted") String isCompleted);
	int insert(Tobuy tobuy);
	int updateAllCompleted();
}
