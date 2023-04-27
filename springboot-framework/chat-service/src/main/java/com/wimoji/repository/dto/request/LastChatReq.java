package com.wimoji.repository.dto.request;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "last_chat")
public class LastChatReq {
	String uid;
	String rid;
}