package com.wimoji.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CharHandler extends TextWebSocketHandler {
	private static List<WebSocketSession> list = new ArrayList<>();

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// String payload = message.getPayload();
		// log.info("payload: " + payload);

		for(WebSocketSession sess : list) {
			sess.sendMessage(message);
		}
	}

	// 클라이언트에서 접속 성공 시 발생 이벤트
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		list.add(session);
		// log.info(session + " 클라이언트 접속");
	}

	// 클라이언트에서 접속 해제 시 발생 이벤트
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		list.remove(session);
		// log.info(session + " 클라이언트 접속 해제");
	}
}