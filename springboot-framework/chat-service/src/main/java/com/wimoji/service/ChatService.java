package com.wimoji.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.wimoji.repository.ChatRoomRepository;
import com.wimoji.repository.dto.ChatDto;
import com.wimoji.repository.dto.ChatRoomReq;
import com.wimoji.repository.dto.ChatRoomRes;
import com.wimoji.repository.entity.ChatRoom;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {
	@Autowired
	private MongoTemplate mongoTemplate;
	private final ChatRoomRepository chatRoomRepository;

	public void makeChatRoom(ChatRoomReq chatRoomReq) {
		ChatRoom chatRoom = ChatRoom.builder()
			.userList(chatRoomReq.getUserList())
			.content(new ArrayList<ChatDto>())
			.build();
		chatRoomRepository.save(chatRoom);
	}

	public List<ChatRoomRes> findAllRooms() {
		return chatRoomRepository.findAllRooms();
	}
}
