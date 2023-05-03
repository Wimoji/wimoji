package com.wimoji.repository.dto.request;

import lombok.Data;

@Data
public class ChatRoomUserReq {
	int eid;
	String title;
	int limit;
}