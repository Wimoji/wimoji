package com.wimoji.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.wimoji.repository.dto.request.ChatRoomReq;
import com.wimoji.repository.dto.request.NewChatReq;
import com.wimoji.repository.dto.response.ChatRes;
import com.wimoji.repository.dto.response.ChatRoomRes;

@Repository
public class ChatRoomRepository {
	@Autowired
	private MongoTemplate mongoTemplate; // mongoDB 사용을 위한 template

	/**
	 * id가 일치하는 채팅방을 반환
	 * @param : 채팅방의 id
	 * @return : 채팅방의 정보 ChatRoomRes 반환
	 **/
	public ChatRoomRes findById(String rid) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(new ObjectId(rid)));

		ChatRoomRes chatRoom = mongoTemplate.findOne(query, ChatRoomRes.class);
		return chatRoom;
	}

	/**
	 * DB에 새로운 정보 저장
	 * @param : ChatRoomReq entity
	 * @return :
	 **/
	public void save(ChatRoomReq chatRoomReq) {
		mongoTemplate.save(chatRoomReq);
	}

	/**
	 * 기존 data에 새로운 메시지 추가
	 * @param : 채팅방의 id, 채팅을 보낸 사람, 채팅 내용
	 * @return :
	 **/
	public void saveContent(ChatRes chat) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(new ObjectId(chat.getRid())));
		Update update = new Update().pull("userList", chat.getSender());
		mongoTemplate.updateFirst(query, update, ChatRoomReq.class);
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
		mongoTemplate.updateFirst(query, update, ChatRoomReq.class);
	}

	/**
	 * 채팅방에 참여 인원을 1 감소
	 * @param : 채팅방의 id
	 * @return :
	 **/
	public void decParticipant(String rid) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(new ObjectId(rid)));
		Update update = new Update().inc("participant", -1);
		mongoTemplate.updateFirst(query, update, ChatRoomReq.class);
	}

	/**
	 * 채팅방의 참여 유저 목록에 새로운 유저 추가
	 * @param : 채팅방의 id, 유저의 id
	 * @return :
	 **/
	public void addUserToList(String rid, String uid) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(new ObjectId(rid)));
		Update update = new Update().addToSet("userList", uid);
		mongoTemplate.updateFirst(query, update, ChatRoomReq.class);
	}

	/**
	 * 채팅방의 참여 유저 목록에서 기존 유저 제거
	 * @param : 채팅방의 id, 유저의 id
	 * @return :
	 **/
	public void deleteUserToList(String rid, String uid) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(new ObjectId(rid)));
		Update update = new Update().pull("userList", uid);
		mongoTemplate.updateFirst(query, update, ChatRoomReq.class);
	}

	/**
	 * 채팅방의 마지막 메세지 인덱스 반환
	 * @param : 채팅방의 id
	 * @return : 채팅방의 메시지 크기
	 **/
	public int getLastChat(String rid) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(new ObjectId(rid)));
		ChatRoomReq lastChat = mongoTemplate.findOne(query, ChatRoomReq.class);

		int listIdx = lastChat.getContent().size();
		return listIdx;
	}

	/**
	 * 유저가 마지막으로 읽은 메시지로부터 10번 먼저 온 메시지부터 새로운 모든 메시지 출력
	 * @param : 채팅방의 id, 메시지를 가져올 시작 인덱스
	 * @return : 메시지의 List
	 **/
	public List<ChatRes> getNewChat(NewChatReq newChatReq) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(new ObjectId(newChatReq.getRid())));
		ChatRoomRes chatRoom = mongoTemplate.findOne(query, ChatRoomRes.class);

		List<ChatRes> content = chatRoom.getContent().subList(newChatReq.getIdx(), chatRoom.getContent().size());
		return content;
	}

	/**
	 * 채팅방에 참여한 유저 id 목록 반환
	 * @param : 채팅방의 id
	 * @return : 유저의 id를 담은 List
	 **/
	public List<String> isExistUser(String rid) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(new ObjectId(rid)));
		query.fields().include("userList");
		ChatRoomRes chatRoom = mongoTemplate.findOne(query, ChatRoomRes.class);

		return chatRoom.getUserList();
	}
}