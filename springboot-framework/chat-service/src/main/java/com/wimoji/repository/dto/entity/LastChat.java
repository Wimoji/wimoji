package com.wimoji.repository.dto.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Document(collection = "last_chat")
public class LastChat {
	@Id
	LastChatId id;
	int idx;
}