package com.linebot.message;

import com.google.common.collect.Lists;
import com.linebot.action.traindelay.TrainDelayTime;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.message.FlexMessage;
import com.linecorp.bot.model.message.flex.component.Box;
import com.linecorp.bot.model.message.flex.component.Button;
import com.linecorp.bot.model.message.flex.component.FlexComponent;
import com.linecorp.bot.model.message.flex.container.Bubble;
import com.linecorp.bot.model.message.flex.container.Carousel;
import com.linecorp.bot.model.message.flex.unit.FlexLayout;
import com.linecorp.bot.model.message.flex.unit.FlexMarginSize;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TrainDelayTimeMessage {
    protected int bobbleRow = 10;

    @NotNull
    public FlexMessage build(@NotNull String alt) {
        List<List<MessageAction>> lists = Lists.partition(this.getMessageActions(), bobbleRow);
        return new FlexMessage(alt, new Carousel(buildBubbles(lists)));
    }

    protected List<MessageAction> getMessageActions() {
        return Arrays.stream(TrainDelayTime.values())
                .map(t -> new MessageAction(t.getTime(), t.getTime()))
                .collect(Collectors.toList());

    }

    @NotNull
    private List<Bubble> buildBubbles(@NotNull List<List<MessageAction>> lists) {
        List<Bubble> bubbles = Lists.newArrayList();
        lists.stream()
                .limit(10)
                .forEach(list -> {
                    bubbles.add(Bubble.builder()
                            .body(buildBody(list))
                            .build()
                    );
                });
        return bubbles;
    }

    @NotNull
    private Box buildBody(@NotNull List<MessageAction> list) {
        List<FlexComponent> bodyContent = Lists.newLinkedList();
        list.stream()
                .forEach(l -> bodyContent.add(this.buildButton(l)));
        return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .flex(0)
                .spacing(FlexMarginSize.SM)
                .contents(bodyContent)
                .build();
    }

    private Button buildButton(@NotNull MessageAction action) {
        return Button.builder()
                .style(Button.ButtonStyle.LINK)
                .height(Button.ButtonHeight.SMALL)
                .margin(FlexMarginSize.SM)
                .action(action)
                .build();
    }
}
