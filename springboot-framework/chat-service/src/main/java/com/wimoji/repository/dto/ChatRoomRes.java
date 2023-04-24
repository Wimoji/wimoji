package com.wimoji.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatRoomRes {
	String emoji;
	String name;
	boolean isNew;
}
