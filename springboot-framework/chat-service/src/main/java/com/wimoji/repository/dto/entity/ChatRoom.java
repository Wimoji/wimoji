package com.wimoji.repository.dto.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import com.wimoji.repository.dto.request.ChatRoomReq;
import com.wimoji.repository.dto.response.UserEnterRes;

import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chat_room")
public class ChatRoom {
	String id; // null
	int eid;
	String title;
	int participant;
	int limit;
	List<UserEnterRes> userList;
	List<Chat> content;

	public ChatRoom(ChatRoomReq chatRoomReq, UserEnterRes userEnterRes) {
		this.id = null;
		this.eid = chatRoomReq.getEid();
		this.title = chatRoomReq.getTitle();
		this.participant = 1;
		this.limit = chatRoomReq.getLimit();
		this.userList = new ArrayList<>();
		userList.add(userEnterRes);
		this.content = new ArrayList<>();
	}
}
