package com.wimoji.repository.dto.request;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Document(collection = "chat")
public class ChatReq {
	String rid;
	String content;
}