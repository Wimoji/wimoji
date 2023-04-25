package com.wimoji.repository.dto.request;

import lombok.Data;

@Data
public class ChatRoomReq {
	// 이모지 정보
	String emoji;
	String name;
}