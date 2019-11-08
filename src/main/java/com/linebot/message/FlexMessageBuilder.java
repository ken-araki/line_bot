package com.linebot.message;

import com.google.common.collect.Lists;
import com.linebot.action.ActionSelector;
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
public class FlexMessageBuilder {
    @NotNull
    public FlexMessage buildStartWordMessage() {
        List<MessageAction> startWords = Arrays.stream(ActionSelector.values())
                .map(a -> a.getStartWord())
                .map(s -> new MessageAction(s, s))
                .collect(Collectors.toList());

        return new FlexMessage("どの操作を実行するか選択してください", buildBubble(startWords));
    }

    @NotNull
    public FlexMessage buildListMessageAction(@NotNull String alt, @NotNull List<MessageAction> actions) {
        List<Bubble> bubbles = Lists.newArrayList();
        List<List<MessageAction>> actionsList = Lists.partition(actions, 5);

        actionsList.stream()
                .forEach(a -> bubbles.add(this.buildBubble(a)));
        return new FlexMessage(alt, new Carousel(bubbles));
    }

    private Bubble buildBubble(List<MessageAction> actions) {
        return Bubble.builder()
                .body(this.buildBox(actions))
                .build();
    }

    private Box buildBox(List<MessageAction> actions) {
        List<FlexComponent> list = Lists.newLinkedList();
        actions.stream()
                .forEach(a -> list.add(this.buildButton(a)));
        return Box.builder()
                .layout(FlexLayout.VERTICAL)
                .flex(0)
                .spacing(FlexMarginSize.SM)
                .contents(list)
                .build();

    }

    private Button buildButton(MessageAction action) {
        return Button.builder()
                .style(Button.ButtonStyle.LINK)
                .height(Button.ButtonHeight.SMALL)
                .margin(FlexMarginSize.MD)
                .action(action)
                .build();
    }
}

