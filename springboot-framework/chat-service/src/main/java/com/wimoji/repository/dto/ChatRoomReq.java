package com.wimoji.repository.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatRoomReq {
	// TODO: emoji 연동
	String emoji;
	String name;
}