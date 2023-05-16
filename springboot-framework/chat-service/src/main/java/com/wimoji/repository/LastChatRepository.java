package com.wimoji.repository;

import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.wimoji.base.GeneralException;
import com.wimoji.base.constant.Code;
import com.wimoji.repository.dto.entity.LastChat;
import com.wimoji.repository.dto.entity.LastChatId;

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
		query.addCriteria(Criteria.where("_id.uid").is(chatReq.getId().getUid())
			.and("_id.rid").is(chatReq.getId().getRid()));
		LastChat lastChat = mongoTemplate.findOne(query, LastChat.class);

		if(lastChat == null) {
			mongoTemplate.save(chatReq);
		} else if(lastChat.getIdx() == 0) {
			Update update = new Update().set("idx", chatReq.getIdx());
			mongoTemplate.updateFirst(query, update, LastChat.class);
		} else {
			Update update = new Update().set("idx", chatReq.getIdx() - 1);
			mongoTemplate.updateFirst(query, update, LastChat.class);
		}
	}

	/**
	 * 유저가 채팅방에서 마지막으로 읽은 메시지 조회
	 * @param : 채팅방의 id, 유저의 id
	 * @return : 마지막 메시지의 인덱스
	 **/
	public int getLastChat(LastChatId id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id.uid").is(id.getUid()).and("_id.rid").is(id.getRid()));
		LastChat lastChat = mongoTemplate.findOne(query, LastChat.class);

		if(lastChat == null) {
			return 0;
		}

		return lastChat.getIdx();
	}

	/**
	 * 퇴장 시 마지막으로 읽은 메시지 삭제
	 * @param : 채팅방의 id, accessToken
	 * @return :
	 **/
	public synchronized void removeLastChat(LastChatId id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id.uid").is(id.getUid()).and("_id.rid").is(id.getRid()));
		Update update = new Update().unset("idx");
		mongoTemplate.updateFirst(query, update, LastChat.class);
	}
}