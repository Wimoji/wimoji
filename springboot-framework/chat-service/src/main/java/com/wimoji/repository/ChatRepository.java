package com.wimoji.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.wimoji.repository.dto.request.ChatReq;
import com.wimoji.repository.dto.request.LastChatReq;
import com.wimoji.repository.dto.response.LastChatRes;

@Repository
public class ChatRepository {
	@Autowired
	private MongoTemplate mongoTemplate; // mongoDB 사용을 위한 template

	public void save(ChatReq chat) {
		mongoTemplate.save(chat);
	}

	public String getLastReadId(LastChatReq lastChat) {
		Query query = new Query();
		query.addCriteria(Criteria.where("uid").is(lastChat.getUid()).and("rid").is(lastChat.getRid()));

		LastChatRes Chat = mongoTemplate.findOne(query, LastChatRes.class);

		return Chat.getCid();
	}
}