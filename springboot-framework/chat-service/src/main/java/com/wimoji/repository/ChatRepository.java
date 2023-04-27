package com.wimoji.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.wimoji.repository.dto.request.ChatReq;

@Repository
public class ChatRepository {
	@Autowired
	private MongoTemplate mongoTemplate; // mongoDB 사용을 위한 template

	public void save(ChatReq chat) {
		mongoTemplate.save(chat);
	}

	public String getLastReadId(String uid) {

	}
}