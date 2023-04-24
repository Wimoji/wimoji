package com.wimoji.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wimoji.repository.ChatRoomRepository;
import com.wimoji.repository.dto.ChatRoomReq;
import com.wimoji.repository.dto.ChatRoomRes;
import com.wimoji.repository.entity.ChatRoom;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
	private final ChatRoomRepository chatRoomRepository;

	public List<ChatRoomRes> findAllRooms() {
		List<ChatRoom> chatRooms = chatRoomRepository.findAll();
		List<ChatRoomRes> roomRes = new ArrayList<>();

		for(ChatRoom chatRoom : chatRooms) {
			ChatRoomRes res = new ChatRoomRes(
				chatRoom.getEmoji(), chatRoom.getName(), false
			);
			roomRes.add(res);
		}

		return roomRes;
	}

	public void makeRoom(ChatRoomReq chatRoomReq) {
		ChatRoom chatRoom = ChatRoom.builder()
			.emoji(chatRoomReq.getEmoji())
			.name(chatRoomReq.getName())
			.participant(1)
			.build();
		chatRoomRepository.save(chatRoom);
		// TODO: USER에 DATA 추가
	}
}