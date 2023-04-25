package com.wimoji.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.wimoji.repository.entity.ChatRoom;

@Component
public class ChatRoomRepository {
	@Autowired
	private MongoTemplate mongoTemplate; // mongoDB 사용을 위한 template

	/**
	 * DB의 모든 채팅방 조회
	 * @param :
	 * @return : chat_room에 있는 모든 data
	 * **/
	public List<ChatRoom> findAll() {
		Query query = new Query();
		return mongoTemplate.find(query, ChatRoom.class);
	}

	/**
	 * DB에 새로운 정보 저장
	 * @param : 채팅방 entity 구조의 ChatRoom
	 * @return :
	 * **/
	public void save(ChatRoom chatRoom) {
		mongoTemplate.save(chatRoom);
	}
}
