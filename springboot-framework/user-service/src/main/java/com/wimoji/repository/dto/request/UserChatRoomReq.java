package com.wimoji.repository.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserChatRoomReq {
	String uid;
	List<String> chatList;
}