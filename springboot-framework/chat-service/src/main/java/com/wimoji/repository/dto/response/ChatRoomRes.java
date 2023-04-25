package com.wimoji.repository.dto.response;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "chat_room")
public class ChatRoomRes {
	// 채팅방의 정보
	String id;
	String emoji;
	String name;
	int participant;
	boolean isNew;

	public ChatRoomRes(String emoji, String name, boolean isNew) {
		this.emoji = emoji;
		this.name = name;
		this.isNew = isNew;
	}
}
