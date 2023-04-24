package com.wimoji.repository;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.wimoji.repository.entity.ChatRoom;

@Component
public class ChatRoomRepository {
	private final MongoTemplate mongoTemplate;

	public ChatRoomRepository(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public List<ChatRoom> findAll() {
		Query query = new Query();
		return mongoTemplate.find(query, ChatRoom.class);
	}

	public void save(ChatRoom chatRoom) {
		mongoTemplate.save(chatRoom);
	}
}
