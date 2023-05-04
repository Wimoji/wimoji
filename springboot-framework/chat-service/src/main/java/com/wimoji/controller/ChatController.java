package com.wimoji.controller;

import java.util.List;

import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.wimoji.base.GeneralException;
import com.wimoji.base.constant.Code;
import com.wimoji.repository.dto.entity.Chat;
import com.wimoji.repository.dto.response.ChatRes;
import com.wimoji.repository.dto.request.ChatReq;
import com.wimoji.repository.dto.response.UserRes;
import com.wimoji.service.ChatRoomService;

import lombok.RequiredArgsConstructor;

import static com.wimoji.config.KafkaConfig.getUserByToken;

@Controller
@RequiredArgsConstructor
public class ChatController {
	private final SimpMessagingTemplate template; // message broker 사용 template
	private final ReplyingKafkaTemplate<String, String, String> kafkaTemplate;
	private final ChatRoomService chatRoomService;

	/**
	 * 사용자간 채팅
	 * @param : 채팅 정보를 담은 ChatReq
	 * @return 채팅을 보낸 채팅방에 대해서만 새로운 채팅 반환
	**/
	@MessageMapping("/chat/message")
	public void chat(@Payload ChatReq chatReq, @Header("Authorization") String accessToken) {
		UserRes user = getUserByToken(kafkaTemplate, accessToken);
		Chat chat = new Chat(chatReq.getRid(), user.getUid(), user.getNickname(), chatReq.getContent());
		// TODO: 세션과 사용자ID 대조하여 구분
		ChatRes chatRes = new ChatRes(chatReq.getRid(), user.getNickname(), chatReq.getContent(), 2);

		try {
			chatRoomService.saveContent(chat);
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
	public void enter(@Header("Authorization") String accessToken, @Header("rid") String rid) {
		UserRes user = getUserByToken(kafkaTemplate, accessToken);

		if(isExist(user.getUid(), rid)) {
			return;
		}

		try {
			chatRoomService.incParticipant(rid);
			chatRoomService.addUserToList(rid, user.getUid());
		} catch (Exception e) {
			throw new GeneralException(Code.INTERNAL_ERROR);
		}

		ChatRes chatRes = new ChatRes(rid, user.getNickname(), user.getNickname() + "님이 입장하였습니다.", 3);
		template.convertAndSend("/sub/chat/" + chatRes.getRid(), chatRes);
	}

	/**
	 * 사용자 퇴장
	 * @param : accessToken, 채팅방의 id
	 * @return :
	**/
	@MessageMapping("/chat/exit")
	public void exit(@Header("Authorization") String accessToken, @Header("rid") String rid) {
		UserRes user = getUserByToken(kafkaTemplate, accessToken);

		try {
			chatRoomService.decParticipant(rid);
			chatRoomService.deleteUserToList(rid, user.getUid());
		} catch (Exception e) {
			throw new GeneralException(Code.INTERNAL_ERROR);
		}

		ChatRes chatRes = new ChatRes(rid, user.getNickname(), user.getNickname() + "님이 퇴장하였습니다.", 3);
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