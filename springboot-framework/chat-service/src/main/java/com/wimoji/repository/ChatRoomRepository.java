package com.wimoji.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.wimoji.repository.dto.ChatRoomRes;
import com.wimoji.repository.entity.ChatRoom;

@Repository
public class ChatRoomRepository {
	@Autowired
	private MongoTemplate mongoTemplate;

	public void save(ChatRoom chatRoom) {
		mongoTemplate.save(chatRoom);
	}

	public List<ChatRoomRes> findAllRooms() {
		List<ChatRoomRes> result = mongoTemplate.findAll(ChatRoomRes.class);

		return result;
	}
}
