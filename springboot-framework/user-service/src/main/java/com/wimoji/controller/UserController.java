package com.wimoji.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wimoji.base.GeneralException;
import com.wimoji.base.constant.Code;
import com.wimoji.base.dto.DataResponseDto;
import com.wimoji.common.JwtTokenUtil;
import com.wimoji.repository.dto.UserEntity;
import com.wimoji.repository.dto.request.UserReq;
import com.wimoji.repository.dto.response.ChatRoomRes;
import com.wimoji.service.ChatServiceClient;
import com.wimoji.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    private final ChatServiceClient chatServiceClient;
    private final ObjectMapper mapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/signup")
    public DataResponseDto<?> makeUser(@RequestBody UserReq userReq) {
        try {
            service.makeUser(userReq);

            return DataResponseDto.empty();
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping("/login")
    public DataResponseDto<?> setLoginUser(@RequestBody UserReq user) {
        try {
            HashMap<String, String> result = service.loginUser(user.getUid(), user.getPassword());

            return DataResponseDto.of(result);
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping("/logout")
    public DataResponseDto<?> setLogoutUser(HttpServletRequest request) {
        try {
            String bearerToken = request.getHeader("Authorization");

            if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
                throw new GeneralException(Code.NO_USER);
            }

            String token = bearerToken.substring(7);
            String uid = jwtTokenUtil.getUserIdFromToken(token);

            service.logoutUser(uid);

            return DataResponseDto.empty();
        } catch (Exception e) {
            throw e;
        }
    }

    @DeleteMapping("/")
    public DataResponseDto<?> deleteUser(HttpServletRequest request){
        try{
            String bearerToken = request.getHeader("Authorization");

            if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
                throw new GeneralException(Code.NO_USER);
            }

            String token = bearerToken.substring(7);
            String uid = jwtTokenUtil.getUserIdFromToken(token);

            service.deleteUser(uid);

            return DataResponseDto.empty();
        }catch (Exception e){
            throw e;
        }

    }

    /**
     *
     * @param bearerToken
     * @return userid, usernickname이 들어있는 userEntity
     * 다른 microservice와의 통신용
     */
    @GetMapping("/userinfo")
    public UserEntity getUser(@RequestParam String bearerToken) {

        try {

            if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
                throw new GeneralException(Code.NO_USER);
            }

            String token = bearerToken.substring(7);
            String uid = jwtTokenUtil.getUserIdFromToken(token);
            String nickname = jwtTokenUtil.getUserNicknameFromToken(token);

            UserEntity user = UserEntity.builder().uid(uid).nickname(nickname).build();

            return user;
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/my")
    public DataResponseDto<?> getMyChatRoom(HttpServletRequest request) {
        try {
            String bearerToken = request.getHeader("Authorization");

            if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
                throw new GeneralException(Code.NO_USER);
            }

            String token = bearerToken.substring(7);
            String uid = jwtTokenUtil.getUserIdFromToken(token);

            List<String> chatList = service.getChatListByUser(uid);
            ChatRoomRes chatRoom = mapper.readValue(chatServiceClient.getRoomByUser(bearerToken, chatList), ChatRoomRes.class);

            return DataResponseDto.of(chatRoom);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
