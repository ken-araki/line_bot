package com.linebot.service.tobuy;

import com.linebot.entity.Tobuy;
import com.linebot.repository.TobuyRepository;
import com.linebot.util.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class TobuyService {
    private TobuyRepository tobuyRepository;

    @Transactional(readOnly = true)
    public List<Tobuy> findByIsCompleted(String userId, String isCompleted) {
        return tobuyRepository.findByUserIdAndIsCompleted(userId, isCompleted);
    }

    @Transactional(readOnly = true)
    public Tobuy findByIdAndGoods(Integer id, String userId, String goods) {
        return tobuyRepository.findByIdAndUserIdAndGoodsAndIsCompleted(id, userId, goods, "0");
    }

    @Transactional
    public int insertByGoods(String userId, String goods) {
        Tobuy t = new Tobuy();
        t.setGoods(goods);
        t.setIsCompleted("0");
        t.setCreatedDate(Utils.now());
        t.setUpdatedDate(Utils.now());
        t.setUserId(userId);
        tobuyRepository.save(t);
        return 1;
    }

    @Transactional
    public int updateCompleted(Tobuy tobuy) {
        tobuy.setIsCompleted("1");
        tobuy.setUpdatedDate(Utils.now());
        tobuyRepository.save(tobuy);
        return 1;
    }

    @Transactional
    public int updateCompleted(List<Tobuy> tobuys) {
        return tobuys.stream().mapToInt(tobuy -> {
            tobuy.setIsCompleted("1");
            tobuy.setUpdatedDate(Utils.now());
            tobuyRepository.save(tobuy);
            return 1;
        }).sum();
    }
}
