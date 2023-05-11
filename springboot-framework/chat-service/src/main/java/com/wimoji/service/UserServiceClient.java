package com.wimoji.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="user-service", url = "127.0.0.1:8080")
public interface UserServiceClient {
    @GetMapping("/api/user-service/userinfo")
    String getUser(@RequestParam("bearerToken") String bearerToken);
}
