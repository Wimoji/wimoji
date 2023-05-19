package com.wimoji.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.wimoji.repository.dto.entity.Chat;
import com.wimoji.repository.dto.entity.ChatRoom;
import com.wimoji.repository.dto.request.NewChatReq;
import com.wimoji.repository.dto.response.ChatRoomRes;
import com.wimoji.repository.dto.response.UserEnterRes;

@Repository
public class ChatRoomRepository {
	@Autowired
	private MongoTemplate mongoTemplate; // mongoDB 사용을 위한 template

	/**
	 * db의 모든 채팅방을 반환(테스트용)
	 * @param : 채팅방의 id
	 * @return : 채팅방의 정보를 담은 ChatRoomRes List 반환
	 **/
	public List<ChatRoomRes> findAll() {
		Query query = new Query();
		return mongoTemplate.find(query, ChatRoomRes.class);
	}

	/**
	 * id가 일치하는 채팅방을 반환
	 * @param : 채팅방의 id
	 * @return : 채팅방의 정보 ChatRoomRes 반환
	 **/
	public ChatRoom findById(String rid) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(new ObjectId(rid)));
		return mongoTemplate.findOne(query, ChatRoom.class);
	}

	/**
	 * DB에 새로운 정보 저장
	 * @param : ChatRoom entity
	 * @return :
	 **/
	public ChatRoom save(ChatRoom chatRoom) {
		return mongoTemplate.insert(chatRoom);
	}

	/**
	 * 기존 data에 새로운 메시지 추가
	 * @param : 채팅방의 id, 채팅을 보낸 사람, 채팅 내용
	 * @return :
	 **/
	public void saveContent(Chat chat) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(new ObjectId(chat.getRid())));
		Update update = new Update().push("content", chat);
		mongoTemplate.updateFirst(query, update, ChatRoom.class);
	}

	/**
	 * 채팅방에 참여 인원을 1 증가
	 * @param : 채팅방의 id
	 * @return :
	 **/
	public void incParticipant(String rid) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(new ObjectId(rid)));
		Update update = new Update().inc("participant", 1);
		mongoTemplate.updateFirst(query, update, ChatRoom.class);
	}

	/**
	 * 채팅방에 참여 인원을 1 감소
	 * @param : 채팅방의 id
	 * @return :
	 **/
	public int decParticipant(String rid) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(new ObjectId(rid)));
		Update update = new Update().inc("participant", -1);

		mongoTemplate.updateFirst(query, update, ChatRoom.class);

		ChatRoom chatRoom = mongoTemplate.findOne(query, ChatRoom.class);
		return chatRoom.getParticipant();
	}

	/**
	 * 채팅방의 참여 유저 목록에 새로운 유저 추가
	 * @param : 채팅방의 id, 유저의 id
	 * @return :
	 **/
	public void addUserToList(String rid, UserEnterRes userEnterRes) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(new ObjectId(rid)));
		Update update = new Update().addToSet("userList", userEnterRes);
		mongoTemplate.updateFirst(query, update, ChatRoom.class);
	}

	/**
	 * 채팅방의 참여 유저 목록에서 기존 유저 제거
	 * @param : 채팅방의 id, 유저의 id
	 * @return :
	 **/
	public void deleteUserToList(String rid, String uid) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(new ObjectId(rid)));
		Update update = new Update().pull("userList", Query.query(Criteria.where("uid").is(uid)));
		mongoTemplate.updateFirst(query, update, ChatRoom.class);
	}

	/**
	 * 유저가 마지막으로 읽은 메시지로부터 10번 먼저 온 메시지부터 새로운 모든 메시지 출력
	 * @param : 채팅방의 id, 메시지를 가져올 시작 인덱스
	 * @return : 메시지의 List
	 **/
	public List<Chat> getNewChat(NewChatReq newChatReq) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(new ObjectId(newChatReq.getRid())));
		ChatRoom chatRoom = mongoTemplate.findOne(query, ChatRoom.class);

		List<Chat> content = chatRoom.getContent().subList(newChatReq.getStartIdx(), chatRoom.getContent().size());
		return content;
	}

	/**
	 * 이전 메시지를 30개씩 조회
	 * @param : 채팅방의 id, 메시지를 가져올 시작 인덱스
	 * @return : 메시지의 List
	 **/
	public List<Chat> getPastChat(NewChatReq newChatReq) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(new ObjectId(newChatReq.getRid())));
		ChatRoom chatRoom = mongoTemplate.findOne(query, ChatRoom.class);

		List<Chat> content = chatRoom.getContent().subList(newChatReq.getStartIdx(), newChatReq.getEndIdx());
		return content;
	}

	/**
	 * 채팅방에 참여한 유저 id 목록 반환
	 * @param : 채팅방의 id
	 * @return : 유저의 id를 담은 List
	 **/
	public ChatRoom isExistUser(String rid) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(new ObjectId(rid)));
		query.fields().include("userList");
		return mongoTemplate.findOne(query, ChatRoom.class);
	}
}