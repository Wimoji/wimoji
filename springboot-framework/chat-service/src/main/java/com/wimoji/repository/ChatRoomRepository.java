package com.wimoji.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.wimoji.repository.dto.request.ChatRoomReq;
import com.wimoji.repository.dto.response.ChatRoomRes;

@Repository
public class ChatRoomRepository {
	@Autowired
	private MongoTemplate mongoTemplate; // mongoDB 사용을 위한 template

	/**
	 * DB의 모든 채팅방 조회
	 *
	 * @param :
	 * @return : chat_room에 있는 모든 data
	 **/
	public List<ChatRoomRes> findAll() {
		Query query = new Query();
		return mongoTemplate.find(query, ChatRoomRes.class);
	}

	/**
	 * DB에 새로운 정보 저장
	 * @param : 채팅방 entity 구조의 ChatRoom
	 * @return :
	 * **/
	public void save(ChatRoomReq chatRoom) {
		mongoTemplate.save(chatRoom);
	}
}
