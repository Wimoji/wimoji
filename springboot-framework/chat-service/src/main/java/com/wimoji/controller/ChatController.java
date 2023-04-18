package com.wimoji.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
	@GetMapping("/chat")
	public String chat() {
		return "chat";
	}
}