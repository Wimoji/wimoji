package com.wimoji.repository.dto;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {
	ObjectId rid;
	String sender;
	String content;
	String sendTime;

	public String getSender() {
		return sender;
	}

	public String getContent() {
		return content;
	}

	public String getSendTime() {
		return sendTime;
	}
}