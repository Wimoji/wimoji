package com.wimoji.repository.dto;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {
	// 채팅 정보
	ObjectId rid;
	String sender;
	String content;
	String sendTime;
}