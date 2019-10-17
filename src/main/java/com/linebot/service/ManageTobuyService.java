package com.linebot.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linebot.domain.Tobuy;
import com.linebot.mapper.TobuyMapper;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;

import lombok.NonNull;

@Slf4j
@AllArgsConstructor
@Service
public class ManageTobuyService {
	private final String LINE_SEPARATOR = System.getProperty("line.separator");

	private LineMessagingClient lineMessagingClient;
	private TobuyMapper mapper;

	@Transactional
	public void execute(String replyToken, Event event, TextMessageContent content) throws Exception {
		String text = content.getText();
		String[] lines = text.split(LINE_SEPARATOR);

		String operation = lines[0];
		switch (operation) {
		case "買い物リスト追加":
			int resultInsert = Arrays.stream(lines)
					.skip(1)
					.mapToInt(this::insertByGoods)
					.sum();
			String messageInsert = String.format("%d 件の買い物リストを登録しました.", resultInsert);
			this.reply(
					replyToken,
					Arrays.asList(new TextMessage(messageInsert))
			);
			break;
		case "買い物リスト確認":
			List<Tobuy> tobuyList = this.findByIsCompleted("0");
			StringBuilder sb = new StringBuilder("買い物リストはこちらです");
			tobuyList.stream().forEach(t -> sb.append(LINE_SEPARATOR + t.getGoods()));
			this.reply(
					replyToken,
					Collections.singletonList(new TextMessage(sb.toString()))
			);
			break;
		case "買い物リスト購入":
			int resultUpdate = this.updateAllCompleted();
			String messageUpdate = String.format("登録されていた %d 件の商品を購入しました", resultUpdate);
			this.reply(
					replyToken,
					Collections.singletonList(new TextMessage(messageUpdate))
			);
			break;
		default:
			log.info("operation [{}] is nothing.", operation);
			break;
		}
	}

	private void reply(@NonNull String replyToken, @NonNull List<Message> messages) {
		try {
			BotApiResponse apiResponse = lineMessagingClient
					.replyMessage(new ReplyMessage(replyToken, messages))
					.get();
			log.info("Sent messages: {}", apiResponse);
		} catch (InterruptedException | ExecutionException e) {
			log.error("replyMessage error: {}", e);
			throw new RuntimeException(e);
		}
	}

	public List<Tobuy> findByIsCompleted(String isCompleted) {
		return mapper.findByIsCompleted(isCompleted);
	}

	public int insertByGoods(String goods) {
		Tobuy t = new Tobuy();
		t.setGoods(goods);
		t.setIsCompleted("0");
		return mapper.insert(t);
	}

	public int updateAllCompleted() {
		return mapper.updateAllCompleted();
	}
}
