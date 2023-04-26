package com.wimoji.repository.dto.request;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "chat_room")
public class ChatRoomReq {
	// 이모지 정보
	String emoji;
	String name;
	int participant;
	int limit;
}