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

	private String name;
}