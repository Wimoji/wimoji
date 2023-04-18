package com.wimoji.repository.entity;

import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.wimoji.repository.dto.Chat;

@Document(collection = "chat_room")
public class ChatRoom {
	@Id
	private String _id;

	// TODO: String[]에서 User[]로 변환
	private String[] userList;

	private boolean[] isExit;

	private List<Chat> content;

	// TODO: Emoji 추가
	// private Emoji emoji;
}