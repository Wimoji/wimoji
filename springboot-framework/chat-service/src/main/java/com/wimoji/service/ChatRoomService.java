package com.wimoji.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.wimoji.repository.ChatRoomRepository;
import com.wimoji.repository.entity.ChatRoom;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
	private final ChatRoomRepository chatRoomRepository;

	public List<ChatRoom> findAllRooms() {
		List<ChatRoom> result = chatRoomRepository.findAll();

		return result;
	}
}
