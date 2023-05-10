package com.wimoji.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wimoji.base.GeneralException;
import com.wimoji.base.constant.Code;
import com.wimoji.base.dto.DataResponseDto;
import com.wimoji.repository.dto.entity.ChatRoom;
import com.wimoji.repository.dto.request.ChatRoomReq;
import com.wimoji.repository.dto.request.NewChatReq;
import com.wimoji.repository.dto.request.UserChatRoomReq;
import com.wimoji.repository.dto.response.ChatRoomRes;
import com.wimoji.repository.dto.response.UserEnterRes;
import com.wimoji.repository.dto.response.UserRes;
import com.wimoji.service.ChatRoomService;
import com.wimoji.service.UserServiceClient;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {

	private final ChatRoomService chatRoomService;
	private final UserServiceClient userServiceClient;
	private final ObjectMapper mapper;

	/**
	 * db의 모든 채팅방을 반환(테스트용)
	 * @param : 채팅방의 id
	 * @return : 채팅방의 정보를 담은 ChatRoomRes List 반환
	 **/
	@GetMapping("/test")
	public DataResponseDto<?> getAllRoom() {
		try {
			List<ChatRoomRes> chatRoomList = chatRoomService.getAllRoom();
			return DataResponseDto.of(chatRoomList);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 유저가 참여한 채팅방 목록 조회 함수
	 * @param : accessToken, 참여한 채팅방의 id를 가진 list
	 * @return : 채팅방의 정보 list
	 **/
	@PostMapping("/my")
	public List<ChatRoomRes> getRoomByUser(@RequestBody UserChatRoomReq userChatRoom) {
		try {
			List<ChatRoomRes> chatRoomList = new ArrayList<>();

			for(String rid : userChatRoom.getChatList()) {
				ChatRoomRes chatRoom = getRoom(userChatRoom.getUid(), rid);
				chatRoomList.add(chatRoom);
			}

			return chatRoomList;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 이모지의 정보를 담은 새로운 채팅방을 생성
	 * @param : accessToken, 이모지의 정보를 담은 ChatRoom
	 * @return : 성공 또는 실패 메세지
	 **/
	@PostMapping("/")
	public DataResponseDto<?> makeRoom(@RequestHeader("Authorization") String accessToken,
		@RequestBody ChatRoomReq chatRoomReq) {
		try {
			UserRes user = mapper.readValue(userServiceClient.getUser(accessToken), UserRes.class);

			UserEnterRes userEnterRes = new UserEnterRes(user.getUid(), 0);
			ChatRoom chatRoom = new ChatRoom(chatRoomReq, userEnterRes);

			String rid = chatRoomService.makeRoom(chatRoom);
			Map<String, String> result = new HashMap<>();
			result.put("rid", rid);

			return DataResponseDto.of(result);
		} catch (JsonProcessingException ex) {
			throw new GeneralException(Code.UNAUTHORIZED);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 유저가 채팅방에서 마지막으로 읽은 메시지 저장
	 * @param : 채팅방의 id, accessToken
	 * @return : 성공 또는 실패 메세지
	 **/
	@PostMapping("/last/{rid}")
	public DataResponseDto<?> makeLastChat(@RequestHeader("Authorization") String accessToken,
		@PathVariable String rid) {
		try {
			UserRes user = mapper.readValue(userServiceClient.getUser(accessToken), UserRes.class);
			chatRoomService.makeLastChat(user.getUid(), rid);

			return DataResponseDto.empty();
		} catch (JsonProcessingException ex) {
			throw new GeneralException(Code.UNAUTHORIZED);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 유저가 채팅방에서 마지막으로 읽은 메시지 조회
	 * @param : 채팅방의 id, accessToken
	 * @return : 마지막 메시지의 인덱스
	 **/
	@GetMapping("/last/{rid}")
	public DataResponseDto<?> getLastChat(@RequestHeader("Authorization") String accessToken,
		@PathVariable String rid) {

		try {
			UserRes user = mapper.readValue(userServiceClient.getUser(accessToken), UserRes.class);
			int lastIdx = chatRoomService.getLastChat(user.getUid(), rid);

			return DataResponseDto.of(lastIdx);
		} catch (JsonProcessingException ex) {
			throw new GeneralException(Code.UNAUTHORIZED);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 유저가 마지막으로 읽은 메시지로부터 15번 먼저 온 메시지부터 새로운 모든 메시지 출력
	 * @param : 채팅방의 id, 마지막 메시지의 인덱스
	 * @return : 메시지의 List, firstIdx
	 **/
	@GetMapping("/unread/{rid}/{idx}")
	public DataResponseDto<?> getNewChat(@RequestHeader("Authorization") String accessToken,
		@PathVariable String rid, @PathVariable int idx) {
		try {
			UserRes user = mapper.readValue(userServiceClient.getUser(accessToken), UserRes.class);

			NewChatReq newChatReq = new NewChatReq(rid, user.getUid(), idx, idx);
			Map<String, List> result = chatRoomService.getNewChat(newChatReq); // chatList, firstIdx

			return DataResponseDto.of(result);
		} catch (JsonProcessingException ex) {
			throw new GeneralException(Code.UNAUTHORIZED);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 이전 메시지를 30개씩 조회
	 * @param : 채팅방의 id, 마지막 메시지의 인덱스
	 * @return : 메시지의 List, firstIdx
	 **/
	@GetMapping("/read/{rid}/{idx}")
	public DataResponseDto<?> getPastChat(@RequestHeader("Authorization") String accessToken,
		@PathVariable String rid, @PathVariable int idx) {
		try {
			UserRes user = mapper.readValue(userServiceClient.getUser(accessToken), UserRes.class);

			NewChatReq newChatReq = new NewChatReq(rid, user.getUid(), idx, idx);
			List<UserEnterRes> userList = chatRoomService.isExistUser(rid);
			int enterIdx = getEnterChat(userList, user.getUid());
			if(enterIdx == -1) {
				throw new GeneralException(Code.NOT_FOUND);
			}

			Map<String, List> result = chatRoomService.getPastChat(newChatReq, enterIdx); // chatList, firstIdx
			return DataResponseDto.of(result);
		} catch (JsonProcessingException ex) {
			throw new GeneralException(Code.UNAUTHORIZED);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * id가 일치하는 채팅방을 반환
	 * @param : 유저의 id, 채팅방의 id
	 * @return : 채팅방의 정보 ChatRoomRes 반환
	 **/
	private ChatRoomRes getRoom(String uid, String rid) {
		try {
			int idx = chatRoomService.getLastChat(uid, rid);
			ChatRoomRes chatRoom = chatRoomService.getRoom(rid, idx);
			return chatRoom;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 유저의 입장 메시지를 조회
	 * @param : 유저의 id, 채팅방 참여 유저 List
	 * @return : 입장 메시지 idx
	 **/
	private int getEnterChat(List<UserEnterRes> userList, String uid) {
		for(UserEnterRes user : userList) {
			if(user.getUid().equals(uid)) {
				return user.getIdx();
			}
		}
		return -1;
	}
}