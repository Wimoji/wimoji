package com.wimoji.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wimoji.service.ChatService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatRoomController {
	private final ChatService chatService;

	@GetMapping("/rooms")
	public ResponseEntity<?> getRooms() {
		return new ResponseEntity(HttpStatus.OK);
	}

	@PostMapping("/room")
	public ResponseEntity<?> makeRoom() {
		return new ResponseEntity(HttpStatus.OK);
	}

	@GetMapping("/room")
	public ResponseEntity<?> getRoom() {
		return new ResponseEntity(HttpStatus.OK);
	}
}
