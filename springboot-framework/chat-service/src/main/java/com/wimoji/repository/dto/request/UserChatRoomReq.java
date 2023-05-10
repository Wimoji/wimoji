package com.wimoji.repository.dto.request;

import java.util.List;

import lombok.Data;

@Data
public class UserChatRoomReq {
	String uid;
	List<String> chatList;
}
