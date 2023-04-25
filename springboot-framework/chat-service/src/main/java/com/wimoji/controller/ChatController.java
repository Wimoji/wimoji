package com.wimoji.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.wimoji.repository.dto.ChatDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {
	// message broker 사용 template
	private final SimpMessagingTemplate template;

	/**
	 * 채팅방 참여 시 알림
	 * @param : 채팅 정보를 담은 ChatDto
	 * @return 참여한 사람의 정보를 알림
	 * **/
	@MessageMapping("/chat/enter")
	public void enter(ChatDto chat) {
		chat.setContent(chat.getSender() + "님이 채팅방에 참여하였습니다.");
		template.convertAndSend("/sub/chat/room" + chat.getRid(), chat);
	}

	/**
	 * 사용자간 채팅
	 * @param : 채팅 정보를 담은 ChatDto
	 * @return 채팅을 보낸 채팅방에 대해서만 새로운 채팅 반환
	 * **/
	@MessageMapping("/chat/message")
	public void chat(ChatDto chat) {
		template.convertAndSend("/sub/chat/" + chat.getRid(), chat);
	}
}