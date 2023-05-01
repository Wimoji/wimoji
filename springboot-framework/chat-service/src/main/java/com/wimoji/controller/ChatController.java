package com.wimoji.controller;

import java.util.List;

import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.wimoji.base.GeneralException;
import com.wimoji.base.constant.Code;
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

		try {
			chatRoomService.saveContent(chatRes);
		} catch (Exception e) {
			throw e;
		}

		template.convertAndSend("/sub/chat/" + chatReq.getRid(), chatRes);
	}

	/**
	 * 사용자 입장
	 * @param : accessToken, 채팅방의 id
	 * @return :
	 **/
	@MessageMapping("/chat/enter")
	public void enter(@Header("token") String token, @Header("rid") String rid) {
		String uid = "1"; // user-service 연동
		String name = "이름"; // user-service 연동

		if(isExist(uid, rid)) {
			return;
		}

		try {
			chatRoomService.incParticipant(rid);
			chatRoomService.addUserToList(rid, uid);
		} catch (Exception e) {
			throw new GeneralException(Code.INTERNAL_ERROR);
		}

		// 환영 메시지
		ChatRes chatRes = new ChatRes(rid, uid, name + "님이 입장하였습니다.");
		template.convertAndSend("/sub/chat/" + chatRes.getRid(), chatRes);
	}

	/**
	 * 사용자 퇴장
	 * @param : accessToken, 채팅방의 id
	 * @return :
	**/
	@MessageMapping("/chat/exit")
	public void exit(@Header("token") String token, @Header("rid") String rid) {
		String uid = "1"; // user-service 연동
		String name = "이름"; // user-service 연동

		try {
			chatRoomService.decParticipant(rid);
			chatRoomService.deleteUserToList(rid, uid);
		} catch (Exception e) {
			throw new GeneralException(Code.INTERNAL_ERROR);
		}

		ChatRes chatRes = new ChatRes(rid, uid, name + "님이 퇴장하였습니다.");
		template.convertAndSend("/sub/chat/" + chatRes.getRid(), chatRes);
	}

	/**
	 * 특정 유저가 특정 채팅방에 존재하는지 확인
	 * @param : 유저의 id, 채팅방의 id
	 * @return : true or false
	 **/
	private boolean isExist(String uid, String rid) {
		List<String> userList = chatRoomService.isExistUser(rid);
		for(String userId : userList) {
			if(userId.equals(uid)) {
				return true;
			}
		}
		return false;
	}
}