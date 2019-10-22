package com.linebot.action;

import com.linebot.entity.Tobuy;
import com.linebot.repository.TobuyRepository;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@AllArgsConstructor
@Service
public class ManageTobuyAction {
	private final String LINE_SEPARATOR = System.getProperty("line.separator");

	private LineMessagingClient lineMessagingClient;
	private TobuyRepository tobuyRepository;

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
			this.updateAllCompleted();
			String messageUpdate = "登録されていた商品を購入しました";
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

	@Transactional(readOnly = true)
	public List<Tobuy> findByIsCompleted(String isCompleted) {
		return tobuyRepository.findByIsCompleted(isCompleted);
	}

	@Transactional
	public int insertByGoods(String goods) {
		Tobuy t = new Tobuy();
		t.setGoods(goods);
		t.setIsCompleted("0");
		tobuyRepository.save(t);
		return 1;
	}

	@Transactional
	public int updateAllCompleted() {
		return tobuyRepository.updateAllCompleted();
	}
}
