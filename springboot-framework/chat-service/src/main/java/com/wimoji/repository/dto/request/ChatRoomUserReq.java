package com.wimoji.repository.dto.request;

import lombok.Data;

@Data
public class ChatRoomUserReq {
	String emoji;
	String name;
	int limit;
}