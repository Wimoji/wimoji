package com.wimoji.repository.dto.response;

import lombok.Data;

@Data
public class ChatRoomRes {
	String id; // 12byte ObjectId
	String rid; // 8자리 짧은 아이디
	int eid;
	String title;
	int participant;
	int limit;
	boolean isNew;
}
