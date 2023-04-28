package com.wimoji.repository.dto.response;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "chat_room")
public class ChatRoomRes {
	String id;
	String rid;
	String emoji;
	String name;
	int participant;
	int limit;
	List<String> userList;

	public static boolean isLimit(ChatRoomRes chatRoom) {
		if(chatRoom.getLimit() <= (chatRoom.getParticipant() + 1)) {
			return false;
		}
		return true;
	}
}