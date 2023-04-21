package com.wimoji.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wimoji.repository.ChatRoomRepository;
import com.wimoji.repository.dto.ChatDto;
import com.wimoji.repository.dto.ChatRoomReq;
import com.wimoji.repository.dto.ChatRoomRes;
import com.wimoji.repository.entity.ChatRoom;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
	private final ChatRoomRepository chatRoomRepository;

	public List<ChatRoomRes> findAllRooms() {
		return chatRoomRepository.findAllRooms();
	}
}
