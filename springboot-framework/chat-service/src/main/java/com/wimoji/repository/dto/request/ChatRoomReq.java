package com.wimoji.repository.dto.request;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.wimoji.repository.dto.response.ChatRes;

import lombok.Data;

@Data
@Document(collection = "chat_room")
public class ChatRoomReq {
	String emoji;
	String name;
	int participant;
	int limit;
	List<String> userList;
	List<ChatRes> content;

	public ChatRoomReq(ChatRoomUserReq chatRoomReq, String uid) {
		this.emoji = chatRoomReq.getEmoji();
		this.name = chatRoomReq.getName();
		this.participant = 1;
		this.limit = chatRoomReq.getLimit();
		this.userList = new ArrayList<>();
		userList.add(uid);
		this.content = new ArrayList<>();
	}
}
