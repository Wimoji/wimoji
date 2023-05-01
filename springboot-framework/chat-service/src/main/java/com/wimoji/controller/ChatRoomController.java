package com.wimoji.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.wimoji.base.GeneralException;
import com.wimoji.base.constant.Code;
import com.wimoji.base.dto.DataResponseDto;
import com.wimoji.repository.dto.request.ChatRoomReq;
import com.wimoji.repository.dto.request.ChatRoomUserReq;
import com.wimoji.repository.dto.request.NewChatReq;
import com.wimoji.repository.dto.response.ChatRoomRes;
import com.wimoji.service.ChatRoomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {
	private final ChatRoomService chatRoomService;

	/**
	 * id가 일치하는 채팅방을 반환
	 * @param : 채팅방의 id
	 * @return : 채팅방의 정보 ChatRoomRes 반환
	 **/
	@GetMapping("/")
	public DataResponseDto<?> getRoom(@RequestHeader("Authorization") String accessToken,
		@RequestBody String rid) {
		try {
			String uid = "1"; // user-service 요청
			ChatRoomRes chatRoom = chatRoomService.getRoom(rid);
			int idx = chatRoomService.getLastChat(uid, rid);
			if(chatRoom.getContent().size() != idx) {
				chatRoom.setNew(true);
			}

			return DataResponseDto.of(chatRoom);
		} catch (Exception e) {
			throw new GeneralException(Code.DATA_ACCESS_ERROR);
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

			chatRoomService.makeRoom(makeChatRoomReq);
			return DataResponseDto.empty();
		} catch (Exception e) {
			throw new GeneralException(Code.BAD_REQUEST);
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
		String uid = "1"; // user-service 요청
		chatRoomService.makeLastChat(uid, rid);

		return DataResponseDto.empty();
	}

	/**
	 * 유저가 채팅방에서 마지막으로 읽은 메시지 조회
	 * @param : 채팅방의 id, accessToken
	 * @return : 마지막 메시지의 인덱스
	 **/
	@GetMapping("/last")
	public DataResponseDto<?> getLastChat(@RequestHeader("Authorization") String accessToken,
		@RequestBody String rid) {
		String uid = "1"; // user-service 요청
		int lastId = chatRoomService.getLastChat(uid, rid);

		return DataResponseDto.of(lastId);
	}

	/**
	 * 유저가 마지막으로 읽은 메시지로부터 10번 먼저 온 메시지부터 새로운 모든 메시지 출력
	 * @param : 채팅방의 id, 마지막 메시지의 인덱스
	 * @return : 메시지의 List
	 **/
	@GetMapping("/unread")
	public DataResponseDto<?> getNewChat(@RequestBody NewChatReq newChatReq) {
		chatRoomService.getNewChat(newChatReq);

		return DataResponseDto.empty();
	}
}