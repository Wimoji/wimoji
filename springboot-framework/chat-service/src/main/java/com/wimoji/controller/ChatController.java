package com.wimoji.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.wimoji.repository.dto.ChatDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {
	private final SimpMessagingTemplate template;

	@MessageMapping("/chat/enter")
	public void enter(ChatDto chat) {
		chat.setContent(chat.getSender() + "님이 채팅방에 참여하였습니다.");
		template.convertAndSend("/sub/chat/room/" + chat.getRid(), chat);
	}

	@MessageMapping("/chat/message")
	public void chat(ChatDto chat) {
		// template.convertAndSend("/seb/chat/room" + chat.getRid(), chat);
		template.convertAndSend("/sub/chat/" + 1, chat);
	}
}