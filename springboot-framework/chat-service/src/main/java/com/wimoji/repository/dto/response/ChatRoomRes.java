package com.wimoji.repository.dto.response;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.wimoji.repository.dto.entity.Chat;
import com.wimoji.repository.dto.entity.ChatRoom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "chat_room")
public class ChatRoomRes {
	String id; // 12byte ObjectId
	String rid; // 8자리 짧은 아이디
	int eid;
	String title;
	int participant;
	int limit;
	boolean isNew;

	public static boolean isLimit(ChatRoomRes chatRoom) {
		if(chatRoom.getLimit() <= (chatRoom.getParticipant() + 1)) {
			return false;
		}
		return true;
	}

	public ChatRoomRes(ChatRoom chatRoom, boolean isNew) {
		this.id = chatRoom.getId();
		this.rid = chatRoom.getId().substring(0, 8);
		this.eid = chatRoom.getEid();
		this.title = chatRoom.getTitle();
		this.participant = chatRoom.getParticipant();
		this.limit = chatRoom.getLimit();
		this.isNew = isNew;
	}
}