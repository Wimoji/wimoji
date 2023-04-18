package com.wimoji.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.wimoji.repository.entity.ChatRoom;

public interface CharRoomRepository extends MongoRepository<ChatRoom, String> {
}
