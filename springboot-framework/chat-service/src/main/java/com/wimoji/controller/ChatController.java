package com.wimoji.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.wimoji.base.GeneralException;
import com.wimoji.base.constant.Code;
import com.wimoji.repository.ChatRepository;
import com.wimoji.repository.ChatRoomRepository;
import com.wimoji.repository.dto.request.ChatReq;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {
	// message broker 사용 template
	private final SimpMessagingTemplate template;
	private final ChatRepository chatRepository;
	private final ChatRoomRepository chatRoomRepository;

	/**
	 * 채팅방 참여 시 알림
	 * @param : 채팅 정보를 담은 ChatReq
	 * @return 참여한 사람의 정보를 알림
	 * **/
	@MessageMapping("/chat/enter")
	public void enter(ChatReq chat) {
		chat.setContent(chat.getSender() + "님이 채팅방에 참여하였습니다.");
		template.convertAndSend("/sub/chat/" + chat.getRid(), chat);
		chatRoomRepository.incParticipant(chat.getRid());
		// 참여 인원에 추가
	}

	/**
	 * 사용자간 채팅
	 * @param : 채팅 정보를 담은 ChatReq
	 * @return 채팅을 보낸 채팅방에 대해서만 새로운 채팅 반환
	 * **/
	@MessageMapping("/chat/message")
	public void chat(ChatReq chat) {
		template.convertAndSend("/sub/chat/" + chat.getRid(), chat);
		try {
			chatRepository.save(chat);
		} catch (Exception e) {
			throw new GeneralException(Code.BAD_REQUEST);
		}
	}

	// DISCONNECT 기능 // 전체 알림 + 채팅방의 인원 감소 + 채팅방 참여 유저에서 삭제
}