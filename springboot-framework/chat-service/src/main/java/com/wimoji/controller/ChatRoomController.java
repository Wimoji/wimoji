package com.wimoji.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.wimoji.base.dto.DataResponseDto;
import com.wimoji.repository.dto.request.ChatRoomReq;
import com.wimoji.repository.dto.request.ChatRoomUserReq;
import com.wimoji.repository.dto.request.NewChatReq;
import com.wimoji.repository.dto.response.ChatRes;
import com.wimoji.repository.dto.response.ChatRoomRes;
import com.wimoji.service.ChatRoomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {
	private final ChatRoomService chatRoomService;

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
	 * 특정 유저가 참여한 모든 채팅방의 정보를 반환
	 * @param : 채팅방의 id를 가진 List
	 * @return : 채팅방의 정보를 담은 ChatRoomRes의 List
	 **/
	@GetMapping("/")
	public DataResponseDto<?> getEnterRoom() {
		return DataResponseDto.empty();
	}

	/**
	 * id가 일치하는 채팅방을 반환
	 * @param : 채팅방의 id
	 * @return : 채팅방의 정보 ChatRoomRes 반환
	 **/
	@GetMapping("/{rid}")
	public DataResponseDto<?> getRoom(@RequestHeader("Authorization") String accessToken,
		@PathVariable String rid) {
		try {
			String uid = "1"; // user-service 요청
			ChatRoomRes chatRoom = chatRoomService.getRoom(rid);
			int idx = chatRoomService.getLastChat(uid, rid);
			if(chatRoom.getContent().size() != idx) {
				chatRoom.setNew(true);
			}
			chatRoom.setContent(new ArrayList<ChatRes>());

			return DataResponseDto.of(chatRoom);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 이모지의 정보를 담은 새로운 채팅방을 생성
	 * @param : accessToken, 이모지의 정보를 담은 ChatRoomReq
	 * @return : 성공 또는 실패 메세지
	 **/
	@PostMapping("/")
	public DataResponseDto<?> makeRoom(@RequestHeader("Authorization") String accessToken,
		@RequestBody ChatRoomUserReq chatRoomReq) {
		try {
			String uid = "1"; // user-service 요청
			ChatRoomReq makeChatRoomReq = new ChatRoomReq(chatRoomReq, uid);

			String rid = chatRoomService.makeRoom(makeChatRoomReq);
			return DataResponseDto.of(rid);
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
			String uid = "1"; // user-service 요청
			chatRoomService.makeLastChat(uid, rid);

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
			String uid = "1"; // user-service 요청
			int lastId = chatRoomService.getLastChat(uid, rid);

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
	public DataResponseDto<?> getNewChat(@PathVariable String rid, @PathVariable int idx) {
		try {
			NewChatReq newChatReq = new NewChatReq(rid, idx);
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
	public DataResponseDto<?> getPastChat(@PathVariable String rid, @PathVariable int idx) {
		NewChatReq newChatReq = new NewChatReq(rid, idx);
		List<ChatRes> pastChatList = chatRoomService.getPastChat(newChatReq);

		return DataResponseDto.of(pastChatList);
	}
}