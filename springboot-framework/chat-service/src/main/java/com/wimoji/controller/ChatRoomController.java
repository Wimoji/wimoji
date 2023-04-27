package com.wimoji.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wimoji.base.GeneralException;
import com.wimoji.base.constant.Code;
import com.wimoji.base.dto.DataResponseDto;
import com.wimoji.repository.dto.request.ChatRoomReq;
import com.wimoji.repository.dto.response.ChatRoomRes;
import com.wimoji.service.ChatRoomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {
	private final ChatRoomService chatRoomService;

	/**
	 * DB의 모든 채팅방을 반환 -> TODO: 헤더로 사용자의 정보를 받아서 해당 사용자가 참여한 모든 채팅방 반환
	 * @param : user의 정보를 담은 AccessToken
	 * @return : 채팅방의 정보 ChatRoomRes 반환
	 * **/
	@GetMapping("/")
	public DataResponseDto<?> getAllRoom() {
		try {
			List<ChatRoomRes> result = chatRoomService.getAllRoom();

			return DataResponseDto.of(result);
		} catch (Exception e) {
			throw new GeneralException(Code.DATA_ACCESS_ERROR);
		}
	}

	/**
	 * 이모지의 정보를 담은 새로운 채팅방을 생성
	 * emoji-service에서 이모지 생성 시 해당 api 호출하고 user db에 data 추가
	 * @param : 이모지의 정보를 담은 ChatRoomReq
	 * @return : 성공 또는 실패 메세지
	 **/
	@PostMapping("/")
	public DataResponseDto<?> makeRoom(@RequestBody ChatRoomReq chatRoomReq) {
		try {
			chatRoomService.makeRoom(chatRoomReq);

			return DataResponseDto.empty();
		} catch (Exception e) {
			throw new GeneralException(Code.BAD_REQUEST);
		}
	}
}