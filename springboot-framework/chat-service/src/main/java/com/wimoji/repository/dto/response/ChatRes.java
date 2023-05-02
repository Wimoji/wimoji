package com.wimoji.repository.dto.response;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatRes {
	String rid;
	String sender;
	String content;
}
