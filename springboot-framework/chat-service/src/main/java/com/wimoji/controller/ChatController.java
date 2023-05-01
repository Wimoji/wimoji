package com.wimoji.controller;

import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.wimoji.repository.dto.response.ChatRes;
import com.wimoji.repository.dto.request.ChatReq;
import com.wimoji.service.ChatRoomService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {
	private final SimpMessagingTemplate template; // message broker 사용 template
	private final ChatRoomService chatRoomService;

	/**
	 * 사용자간 채팅
	 * @param : 채팅 정보를 담은 ChatReq
	 * @return 채팅을 보낸 채팅방에 대해서만 새로운 채팅 반환
	**/
	@MessageMapping("/chat/message")
	public void chat(@Payload ChatReq chatReq, @Header("token") String token) {
		String uid = "1"; // user-service 연동
		String name = "이름"; // user-service 연동
		ChatRes chatRes = new ChatRes(chatReq.getRid(), name, chatReq.getContent());
		chatRoomService.saveContent(chatRes);

		template.convertAndSend("/sub/chatRes/" + chatReq.getRid(), chatRes);
	}

	/**
	 * 사용자 퇴장
	 * @param : accessToken, 채팅방의 id
	 * @return :
	**/
	@MessageMapping("/chat/exit")
	public void chat(@Header("token") String token, @Header("rid") String rid) {
		String uid = "1"; // user-service 연동
		String name = "이름"; // user-service 연동
		chatRoomService.decParticipant(rid);
		chatRoomService.deleteUserToList(rid, uid);

		ChatRes chatRes = new ChatRes(rid, uid, name + "님이 퇴장하였습니다.");
		template.convertAndSend("/sub/chatRes/" + chatRes.getRid(), chatRes);
	}
}