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

		// 24글자 대신 8글자를 rid로 사용하기 위해서 변환
		for(int i=0; i<chatRooms.size(); i++) {
			ChatRoomRes chatRoom = chatRooms.get(i);
			chatRoom.setRid(chatRoom.getId().substring(0, 8));
		}

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
	}
}