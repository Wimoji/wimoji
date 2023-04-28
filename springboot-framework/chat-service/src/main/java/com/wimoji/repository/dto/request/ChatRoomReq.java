package com.wimoji.repository.dto.request;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "chat_room")
public class ChatRoomReq {
	String emoji;
	String name;
	int participant;
	int limit;
	List<String> userList;
}