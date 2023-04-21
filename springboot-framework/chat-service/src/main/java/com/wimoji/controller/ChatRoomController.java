package com.wimoji.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wimoji.base.dto.DataResponseDto;
import com.wimoji.repository.entity.ChatRoom;
import com.wimoji.service.ChatRoomService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/chatroom")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class ChatRoomController {
	private final ChatRoomService chatRoomService;

	@GetMapping("")
	public DataResponseDto<?> getRooms() {
		List<ChatRoom> result = chatRoomService.findAllRooms();
		log.info(result.toString());
		return DataResponseDto.of(result);
	}

	@PostMapping("")
	public ResponseEntity<?> makeRoom() {
		return new ResponseEntity(HttpStatus.OK);
	}

	@DeleteMapping("")
	public ResponseEntity<?> getRoom() {
		return new ResponseEntity(HttpStatus.OK);
	}
}
