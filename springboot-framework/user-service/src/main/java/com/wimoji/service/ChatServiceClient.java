package com.wimoji.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="chat-service", url = "127.0.0.1:8080")
public interface ChatServiceClient {
	@GetMapping("/api/chat-service/my")
	String getRoomByUser(@RequestHeader("Authorization") String accessToken,
		@RequestBody List<String> chatList);
}
