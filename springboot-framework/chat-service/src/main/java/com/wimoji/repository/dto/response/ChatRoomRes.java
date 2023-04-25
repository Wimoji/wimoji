package com.wimoji.repository.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatRoomRes {
	// 채팅방의 정보
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
