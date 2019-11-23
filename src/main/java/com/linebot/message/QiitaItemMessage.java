package com.linebot.message;

import com.google.common.collect.Lists;
import com.linebot.model.qiita.ItemSummary;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.flex.component.Box;
import com.linecorp.bot.model.message.flex.component.FlexComponent;
import com.linecorp.bot.model.message.flex.component.Separator;
import com.linecorp.bot.model.message.flex.component.Text;
import com.linecorp.bot.model.message.flex.container.Bubble;
import com.linecorp.bot.model.message.flex.container.Carousel;
import com.linecorp.bot.model.message.flex.unit.FlexAlign;
import com.linecorp.bot.model.message.flex.unit.FlexFontSize;
import com.linecorp.bot.model.message.flex.unit.FlexLayout;
import com.linecorp.bot.model.message.flex.unit.FlexMarginSize;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class QiitaItemMessage {

    /**
     * Qiitaの記事を3つずつCarousel化する
     * Carouselの最大数は10なので、表示可能な記事は30件となる
     * （sortは事前に行っておくこと）
     *
     * @param qiitaId qiitaユーザ名
     * @param list qiitaユーザに紐づく記事一覧
     * @return 配信メッセージ
     */
    @NotNull
    public FlexMessage build(@NotNull String qiitaId, @NotNull List<ItemSummary> list) {
        String alt = String.format("%sさんの記事一覧", qiitaId);
        List<List<ItemSummary>> lists = Lists.partition(list, 3);
        return new FlexMessage(alt, new Carousel(buildBubbles(qiitaId, lists)));
    }

    @NotNull
    private List<Bubble> buildBubbles(@NotNull String qiitaId, List<List<ItemSummary>> lists) {
        List<Bubble> bubbles = Lists.newArrayList();
        lists.stream()
                .limit(10)
                .forEach(list -> {
                    Box body = buildBody(qiitaId, list);
                    bubbles.add(Bubble.builder()
                            .body(body)
                            .build()
                    );
                });
        return bubbles;
    }

    @NotNull
    private Box buildBody(@NotNull String qiitaId, @NotNull List<ItemSummary> list) {
        List<FlexComponent> body = Lists.newArrayList(
                buildItem(qiitaId, list.get(0))
        );
        // 2つ目以降はSeparatorを用意する
        list.stream()
                .skip(1)
                .forEach(item -> {
                            body.add(Separator.builder()
                                    .margin(FlexMarginSize.XXL)
                                    .build());
                            body.add(buildItem(qiitaId, item));
                        }
                );
        return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .contents(body)
                .build();
    }

    @NotNull
    private Box buildItem(@NotNull String qiitaId, @NotNull ItemSummary item) {
        URIAction action = new URIAction(
                "item link",
                String.format("https://qiita.com/%s/items/%s", qiitaId, item.getId()),
                null
        );
        return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .contents(
                        Arrays.asList(
                                buildTitle(item.getTitle()),
                                buildCounter(item)
                        )
                )
                .action(action)
                .build();
    }

    @NotNull
    private Text buildTitle(@NotNull String title) {
        return Text.builder()
                .size(FlexFontSize.LG)
                .text(title)
                .wrap(true)
                .weight(Text.TextWeight.BOLD)
                .margin(FlexMarginSize.MD)
                .build();
    }

    @NotNull
    private Box buildCounter(@NotNull ItemSummary item) {
        return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .contents(
                        Arrays.asList(
                                buildCounterRecord("いいね", item.getLikesCount()),
                                buildCounterRecord("ストック", item.getStockersCount())
                        )
                )
                .build();
    }

    @NotNull
    private Box buildCounterRecord(@NotNull String word, int count) {
        return Box.builder()
                .layout(FlexLayout.HORIZONTAL)
                .contents(
                        Arrays.asList(
                                buildCounterTitle(word),
                                buildCounterValue(count)
                        )
                )
                .build();
    }

    @NotNull
    private Text buildCounterTitle(@NotNull String word) {
        return Text.builder()
                .flex(0)
                .text(word)
                .size(FlexFontSize.SM)
                .color("#555555")
                .build();
    }

    @NotNull
    private Text buildCounterValue(int count) {
        return Text.builder()
                .text(String.valueOf(count))
                .size(FlexFontSize.SM)
                .color("#111111")
                .align(FlexAlign.END)
                .build();
    }
}

