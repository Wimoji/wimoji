package com.wimoji.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.wimoji.base.dto.DataResponseDto;
import com.wimoji.repository.dto.entity.ChatRoom;
import com.wimoji.repository.dto.request.ChatRoomReq;
import com.wimoji.repository.dto.request.NewChatReq;
import com.wimoji.repository.dto.response.ChatRes;
import com.wimoji.repository.dto.response.ChatRoomRes;
import com.wimoji.repository.dto.response.UserRes;
import com.wimoji.service.ChatRoomService;

import lombok.RequiredArgsConstructor;

import static com.wimoji.config.KafkaConfig.getUserByToken;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {
	private final ChatRoomService chatRoomService;
	private final ReplyingKafkaTemplate<String, String, String> template;

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
	public DataResponseDto<?> getRoomByUser(@RequestHeader("Authorization") String accessToken,
		@RequestBody List<String> chatList) {
		try {
			UserRes user = getUserByToken(template, accessToken);
			List<ChatRoomRes> chatRoomList = new ArrayList<>();

			for(String rid : chatList) {
				ChatRoomRes chatRoom = getRoom(user.getUid(), rid);
				chatRoomList.add(chatRoom);
			}

			return DataResponseDto.of(chatRoomList);
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
			UserRes user = getUserByToken(template, accessToken);

			ChatRoom chatRoom = new ChatRoom(chatRoomReq, user.getUid());

			String rid = chatRoomService.makeRoom(chatRoom);
			Map<String, String> result = new HashMap<>();
			result.put("rid", rid);

			return DataResponseDto.of(result);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 유저가 채팅방에서 마지막으로 읽은 메시지 저장
	 * @param : 채팅방의 id, accessToken
	 * @return : 성공 또는 실패 메세지
	 **/
	@PostMapping("/last")
	public DataResponseDto<?> makeLastChat(@RequestHeader("Authorization") String accessToken,
		@RequestBody String rid) {

		try {
			UserRes user = getUserByToken(template, accessToken);
			chatRoomService.makeLastChat(user.getUid(), rid);

			return DataResponseDto.empty();
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
			UserRes user = getUserByToken(template, accessToken);
			int lastId = chatRoomService.getLastChat(user.getUid(), rid);

			return DataResponseDto.of(lastId);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 유저가 마지막으로 읽은 메시지로부터 10번 먼저 온 메시지부터 새로운 모든 메시지 출력
	 * @param : 채팅방의 id, 마지막 메시지의 인덱스
	 * @return : 메시지의 List
	 **/
	@GetMapping("/unread/{rid}/{idx}")
	public DataResponseDto<?> getNewChat(@RequestHeader("Authorization") String accessToken,
		@PathVariable String rid, @PathVariable int idx) {
		try {
			UserRes user = getUserByToken(template, accessToken);

			NewChatReq newChatReq = new NewChatReq(rid, user.getUid(), idx);
			List<ChatRes> newChatList = chatRoomService.getNewChat(newChatReq);

			return DataResponseDto.of(newChatList);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 이전 메시지를 30개씩 조회
	 * TODO: 유저가 참여하기 이전에 채팅방에 존재했던 메시지에 대한 처리
	 * @param : 채팅방의 id, 마지막 메시지의 인덱스
	 * @return : 메시지의 List
	 **/
	@GetMapping("/read/{rid}/{idx}")
	public DataResponseDto<?> getPastChat(@RequestHeader("Authorization") String accessToken,
		@PathVariable String rid, @PathVariable int idx) {
		try {
			UserRes user = getUserByToken(template, accessToken);

			NewChatReq newChatReq = new NewChatReq(rid, user.getUid(), idx);
			List<ChatRes> pastChatList = chatRoomService.getPastChat(newChatReq);

			return DataResponseDto.of(pastChatList);
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
}