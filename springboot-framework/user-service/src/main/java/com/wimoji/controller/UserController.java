package com.wimoji.controller;

import com.wimoji.base.dto.DataResponseDto;
import com.wimoji.repository.dto.request.UserReqDto;
import com.wimoji.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping("/signup")
    public DataResponseDto<?> makeUser(@RequestBody UserReqDto user) {

        try {
            service.makeUser(user.getUid(), user.getPassword(), user.getNickname());

            return DataResponseDto.empty();
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping("/login")
    public DataResponseDto<?> setLoginUser(@RequestBody UserReqDto user) {
        try {
            service.loginUser(user.getUid(), user.getPassword());

            return DataResponseDto.empty();
        } catch (Exception e) {
            throw e;
        }
    }
}
