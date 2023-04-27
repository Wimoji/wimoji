package com.wimoji.repository.dto.response;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chat")
public class ChatRes {
	String id; // ObjectId를 String으로 받음
	String sender;
	String content;
}
