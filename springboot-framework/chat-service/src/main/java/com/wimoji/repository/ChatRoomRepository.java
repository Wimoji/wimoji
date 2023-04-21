package com.wimoji.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.wimoji.repository.entity.ChatRoom;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
	List<ChatRoom> findAll();
}
