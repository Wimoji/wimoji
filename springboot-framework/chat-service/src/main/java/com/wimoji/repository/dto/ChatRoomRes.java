package com.wimoji.repository.dto;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatRoomRes {
	ObjectId rid;
	String emoji;
	String name;
	boolean isNew;
}
