package com.wimoji.repository.dto.request;

import lombok.Data;

@Data
public class ChatRoomReq {
	int eid;
	String title;
	int limit;
}