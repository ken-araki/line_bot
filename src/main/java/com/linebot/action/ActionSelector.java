package com.linebot.action;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public enum ActionSelector {
    TRAIN_DELAY("電車遅延設定", Arrays.asList("trainDelayInputAction", "TrainDelayAction")),
    TOBUY_ADD("買い物リスト追加", Arrays.asList("tobuyAddInputAction", "tobuyAddAction")),
    TOBUY_CONFIRM("買い物リスト確認", Arrays.asList("tobuyConfirmAction")),
    TOBUY_COMPLATE("買い物リスト購入", Arrays.asList("tobuyComplateInputAction", "tobuyAddAction")),
    QIITA_SETTING("qiita設定", Arrays.asList("qiitaIdInputAction", "qiitaIdAction")),
    QIITA_ITEM_LIST("qiita記事一覧取得", Arrays.asList("qiitaItemAction")),
    ORDER("要望・バグ", Arrays.asList("orderInputAction", "orderAction"));

    @Getter
    private String startWord;
    @Getter
    private List<String> actionList;

    @Nullable
    public static ActionSelector getByStartWord(@NotNull String message) {
        for (ActionSelector action : values()) {
            if (action.getStartWord().equals(message)) {
                return action;
            }
        }
        return null;
    }

}
