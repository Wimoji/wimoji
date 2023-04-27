package com.wimoji.repository.dto.response;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "last_chat")
public class LastChatRes {
	String uid;
	String rid;
	String cid;
}
