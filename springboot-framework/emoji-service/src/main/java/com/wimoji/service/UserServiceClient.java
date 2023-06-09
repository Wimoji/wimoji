package com.wimoji.service;

import com.wimoji.base.dto.DataResponseDto;
import com.wimoji.repository.Entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="user-service", url = "k8a501.p.ssafy.io:8080")
public interface UserServiceClient {
    @GetMapping("/api/user-service/userinfo")
    String getUser(@RequestParam("bearerToken") String bearerToken);
}
