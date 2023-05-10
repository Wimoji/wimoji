package com.wimoji.repository.dto.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Document(collection = "last_chat")
public class LastChat {
	String uid;
	String rid;
	int idx;
}