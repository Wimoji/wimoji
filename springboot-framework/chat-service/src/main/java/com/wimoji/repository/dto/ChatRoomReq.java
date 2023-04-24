package com.wimoji.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatRoomReq {
	// TODO: emoji 연동
	String emoji;
	String name;
}