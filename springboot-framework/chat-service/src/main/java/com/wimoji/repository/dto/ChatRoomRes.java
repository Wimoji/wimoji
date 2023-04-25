package com.wimoji.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatRoomRes {
	String rid;
	String emoji;
	String name;
	boolean isNew;

	public ChatRoomRes(String emoji, String name, boolean isNew) {
		this.emoji = emoji;
		this.name = name;
		this.isNew = isNew;
	}
}
