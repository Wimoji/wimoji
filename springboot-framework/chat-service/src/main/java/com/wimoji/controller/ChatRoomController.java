package com.wimoji.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wimoji.base.dto.DataResponseDto;
import com.wimoji.repository.dto.ChatRoomReq;
import com.wimoji.repository.dto.ChatRoomRes;
import com.wimoji.service.ChatRoomService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/chatroom")
@RequiredArgsConstructor
public class ChatRoomController {
	private final ChatRoomService chatRoomService;

	@GetMapping()
	public DataResponseDto<?> getRooms() {
		List<ChatRoomRes> result = chatRoomService.findAllRooms();
		return DataResponseDto.of(result);
	}

	@PostMapping()
	public ResponseEntity<?> makeRoom(@RequestBody ChatRoomReq chatRoomReq) {
		log.info("만들기 요청");
		log.info(chatRoomReq.getEmoji());
		log.info(chatRoomReq.getName());
		chatRoomService.makeRoom(chatRoomReq);

		return new ResponseEntity(HttpStatus.OK);
	}

	@DeleteMapping()
	public ResponseEntity<?> getRoom() {
		return new ResponseEntity(HttpStatus.OK);
	}

	@PutMapping()
	public ResponseEntity<?> enterRoom() {
		return new ResponseEntity(HttpStatus.OK);
	}
}
