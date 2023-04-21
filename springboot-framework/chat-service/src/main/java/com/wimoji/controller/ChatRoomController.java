package com.wimoji.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wimoji.base.dto.DataResponseDto;
import com.wimoji.service.ChatRoomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/chatroom")
@RequiredArgsConstructor
public class ChatRoomController {
	private final ChatRoomService chatRoomService;

	@GetMapping("")
	public DataResponseDto getRooms() {
		DataResponseDto dataResponseDto = DataResponseDto.of(chatRoomService.findAllRooms());

		return dataResponseDto;
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
