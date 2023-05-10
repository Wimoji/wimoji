package com.wimoji.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.wimoji.repository.dto.entity.LastChat;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LastChatRepository {
	@Autowired
	private MongoTemplate mongoTemplate; // mongoDB 사용을 위한 template

	/**
	 * 유저가 채팅방에서 마지막으로 읽은 메시지 저장
	 * @param : 채팅방의 id, 유저의 id, 메시지의 id
	 * @return :
	 **/
	public void save(LastChat chatReq) {
		Query query = new Query();
		query.addCriteria(Criteria.where("uid").is(chatReq.getUid()).and("rid").is(chatReq.getRid()));
		LastChat lastChat = mongoTemplate.findOne(query, LastChat.class);

		if(lastChat == null) {
			mongoTemplate.save(chatReq);
		}

		Update update = new Update().set("idx", chatReq.getIdx()-1);
		mongoTemplate.updateFirst(query, update, LastChat.class);
	}

	/**
	 * 유저가 채팅방에서 마지막으로 읽은 메시지 조회
	 * @param : 채팅방의 id, 유저의 id
	 * @return : 마지막 메시지의 인덱스
	 **/
	public int getLastChat(String uid, String rid) {
		Query query = new Query();
		query.addCriteria(Criteria.where("uid").is(uid).and("rid").is(rid));
		LastChat lastChat = mongoTemplate.findOne(query, LastChat.class);

		if(lastChat == null) {
			return 0;
		}

		return lastChat.getIdx();
	}
}