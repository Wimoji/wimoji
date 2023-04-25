package com.wimoji.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wimoji.repository.ChatRoomRepository;
import com.wimoji.repository.dto.request.ChatRoomReq;
import com.wimoji.repository.dto.response.ChatRoomRes;
import com.wimoji.repository.entity.ChatRoom;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
	private final ChatRoomRepository chatRoomRepository;

	/**
	 * DB의 모든 채팅방 조회
	 * @param :
	 * @return : 채팅방의 정보를 담은 ChatRoomRes
	 * **/
	public List<ChatRoomRes> getAllRoom() {
		List<ChatRoom> chatRooms = chatRoomRepository.findAll();
		List<ChatRoomRes> roomRes = new ArrayList<>();

		// DB의 정보을 그대로 쓰지 않고 별도의 DTO에 담아서 반환
		for(ChatRoom chatRoom : chatRooms) {
			ChatRoomRes res = new ChatRoomRes(
				chatRoom.getEmoji(), chatRoom.getName(), false
			);
			// ObjectId가 아닌 String으로 반환하도록 저장
			res.setRid(chatRoom.getId().toString());
			roomRes.add(res);
		}

		return roomRes;
	}

	/**
	 * DB에 새로운 정보 저장
	 * @param : 이모지 정보를 담은 ChatRoomReq
	 * @return :
	 * **/
	public void makeRoom(ChatRoomReq chatRoomReq) {
		// 이모지 정보를 chat_room 형식에 맞춰서 변환
		ChatRoom chatRoom = ChatRoom.builder()
			.emoji(chatRoomReq.getEmoji())
			.name(chatRoomReq.getName())
			.participant(1)
			.build();

		chatRoomRepository.save(chatRoom);
		// TODO: USER에 DATA 추가
	}
}