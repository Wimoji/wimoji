package com.wimoji.repository.entity;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.wimoji.repository.dto.ChatDto;

import lombok.Builder;

@Builder
@Document(collection = "chat_room")
public class ChatRoom {
	@Id
	private ObjectId _id;

	// TODO: String에서 User로 변환
	private List<String> userList;

	// TODO: 나간 유저 표시
	// private boolean[] isExit;

	private List<ChatDto> content;

	// TODO: Emoji 추가
	// private Emoji emoji;

	// private Set<WebSocketSession> sessions = new HashSet<>();
}