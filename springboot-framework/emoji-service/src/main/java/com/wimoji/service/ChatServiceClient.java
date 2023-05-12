package com.wimoji.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="chat-service", url = "127.0.0.1:8080")
public interface ChatServiceClient {
	@GetMapping("/api/chat-service/")
	String getNumber(@RequestParam("rid") String rid);
}