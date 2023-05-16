package com.wimoji.controller;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wimoji.base.GeneralException;
import com.wimoji.base.constant.Code;
import com.wimoji.repository.dto.entity.Chat;
import com.wimoji.repository.dto.response.ChatRes;
import com.wimoji.repository.dto.request.ChatReq;
import com.wimoji.repository.dto.response.UserEnterRes;
import com.wimoji.repository.dto.response.UserRes;
import com.wimoji.service.ChatRoomService;
import com.wimoji.service.UserServiceClient;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {
	private final UserServiceClient userServiceClient;
	private final ObjectMapper mapper;
	private final SimpMessagingTemplate template; // message broker 사용 template
	private final ChatRoomService chatRoomService;

	/**
	 * 사용자간 채팅
	 * @param : 채팅 정보를 담은 ChatReq
	 * @return 채팅을 보낸 채팅방에 대해서만 새로운 채팅 반환
	**/
	@MessageMapping("/chat/message")
	public void chat(@Payload ChatReq chatReq, @Header("Authorization") String bearerToken,
		@Header("simpSessionId") String sessionId) {
		try {
			UserRes user = mapper.readValue(userServiceClient.getUser(bearerToken), UserRes.class);

			Chat chat = new Chat(chatReq.getRid(), user.getUid(), user.getNickname(), chatReq.getContent(), LocalDateTime.now());
			chatRoomService.saveContent(chat);

			ChatRes chatRes = new ChatRes(chatReq.getRid(), user.getNickname(), chatReq.getContent(), sessionId, LocalDateTime.now());
			template.convertAndSend("/sub/chat/" + chatReq.getRid(), chatRes);
		} catch (JsonProcessingException je) {
			throw new GeneralException(Code.UNAUTHORIZED);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 사용자 입장
	 * @param : accessToken, 채팅방의 id
	 * @return :
	 **/
	@MessageMapping("/chat/enter")
	public void enter(@Header("Authorization") String bearerToken, @Payload String rid) {
		try {
			UserRes user = mapper.readValue(userServiceClient.getUser(bearerToken), UserRes.class);
			rid = rid.replace("\"", "");
			if(isExist(user.getUid(), rid)) {
				return;
			}
			chatRoomService.incParticipant(rid);
			chatRoomService.addUserToList(rid, user.getUid());

			Chat chat = new Chat(rid, "coment", user.getNickname(), user.getNickname() + "님이 입장하였습니다.", LocalDateTime.now());
			chatRoomService.saveContent(chat);

			ChatRes chatRes = new ChatRes(rid, user.getNickname(), user.getNickname() + "님이 입장하였습니다.", "3", LocalDateTime.now());
			template.convertAndSend("/sub/chat/" + chatRes.getRid(), chatRes);
		} catch (JsonProcessingException je) {
			throw new GeneralException(Code.UNAUTHORIZED);
		} catch (Exception e) {
			throw new GeneralException(Code.INTERNAL_ERROR);
		}
	}

	/**
	 * 사용자 퇴장
	 * @param : accessToken, 채팅방의 id
	 * @return :
	**/
	@MessageMapping("/chat/exit")
	public void exit(@Header("Authorization") String bearerToken, @Payload String rid) {
		try {
			UserRes user = mapper.readValue(userServiceClient.getUser(bearerToken), UserRes.class);
			rid = rid.replace("\"", "");

			chatRoomService.decParticipant(rid);
			chatRoomService.deleteUserToList(rid, user.getUid());

			Chat chat = new Chat(rid, "coment", user.getNickname(), user.getNickname() + "님이 퇴장하였습니다.", LocalDateTime.now());
			chatRoomService.saveContent(chat);

			ChatRes chatRes = new ChatRes(rid, user.getNickname(), user.getNickname() + "님이 퇴장하였습니다.", "3", LocalDateTime.now());
			template.convertAndSend("/sub/chat/" + chatRes.getRid(), chatRes);
		} catch (JsonProcessingException je) {
			throw new GeneralException(Code.UNAUTHORIZED);
		} catch (Exception e) {
			throw new GeneralException(Code.INTERNAL_ERROR);
		}
	}

	/**
	 * 특정 유저가 특정 채팅방에 존재하는지 확인
	 * @param : 유저의 id, 채팅방의 id
	 * @return : true or false
	 **/
	private boolean isExist(String uid, String rid) {
		List<UserEnterRes> userList = chatRoomService.isExistUser(rid);
		for(UserEnterRes user : userList) {
			if(user.getUid().equals(uid)) {
				return true;
			}
		}
		return false;
	}
}