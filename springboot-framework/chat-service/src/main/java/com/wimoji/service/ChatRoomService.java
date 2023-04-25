package com.wimoji.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wimoji.repository.ChatRoomRepository;
import com.wimoji.repository.dto.request.ChatRoomReq;
import com.wimoji.repository.dto.response.ChatRoomRes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
	private final ChatRoomRepository chatRoomRepository;
	private static final int initParticipant = 1;

	/**
	 * DB의 모든 채팅방 조회
	 * @param :
	 * @return : 채팅방의 정보를 담은 ChatRoomRes
	 * **/
	public List<ChatRoomRes> getAllRoom() {
		List<ChatRoomRes> chatRooms = chatRoomRepository.findAll();

		return chatRooms;
	}

	/**
	 * DB에 새로운 정보 저장
	 * @param : 이모지 정보를 담은 ChatRoomReq
	 * @return :
	 * **/
	public void makeRoom(ChatRoomReq chatRoomReq) {
		// 이모지 정보를 chat_room 형식에 맞춰서 변환
		chatRoomRepository.save(chatRoomReq);
		// TODO: USER에 DATA 추가
	}
}