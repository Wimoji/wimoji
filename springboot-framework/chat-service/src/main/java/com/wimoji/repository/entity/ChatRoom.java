package com.wimoji.repository.entity;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chat_room")
public class ChatRoom {
	// 채팅방 Document
	private ObjectId id;
	private String emoji;
	private String name;
	private int participant;
	// private List<Chat> chatList;
}