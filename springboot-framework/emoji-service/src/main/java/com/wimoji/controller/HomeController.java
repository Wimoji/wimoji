package com.wimoji.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wimoji.base.GeneralException;
import com.wimoji.base.constant.Code;
import com.wimoji.base.dto.DataResponseDto;
import com.wimoji.repository.Entity.User;
import com.wimoji.repository.dto.request.HomeReq;
import com.wimoji.repository.dto.response.HomeRes;
import com.wimoji.repository.dto.response.NumberRes;
import com.wimoji.service.ChatServiceClient;
import com.wimoji.service.HomeService;
import com.wimoji.service.UserServiceClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final HomeService homeService;
    private final UserServiceClient userServiceClient;
    private final ChatServiceClient chatServiceClient;
    private final ObjectMapper mapper;

    /**
     * 현재 위치 기반 주변 이모지 가져오기
     * @param location
     * @return
     */
    @PostMapping("/location")
    public DataResponseDto<List<HomeRes>> getOtherEmojiList(HttpServletRequest request, @RequestBody HomeReq location){
        try{
            String bearerToken = request.getHeader("Authorization");
            User user =  mapper.readValue(userServiceClient.getUser(bearerToken), User.class);

            List<HomeRes> homeResList =
                homeService.getOtherEmojiList(user.getUid(), location);

            for(HomeRes homeRes : homeResList) {
                NumberRes numberRes = mapper.readValue(chatServiceClient.getNumber(homeRes.getRid()), NumberRes.class);
                homeRes.setParticipant(numberRes.getParticipant());
                homeRes.setLimit(numberRes.getLimit());
                homeRes.setEnter(numberRes.isEnter());
            }

            return DataResponseDto.of(homeResList, Code.SUCCESS, Code.SUCCESS.getMessage());
        } catch (JsonProcessingException je) {
            throw new GeneralException(Code.UNAUTHORIZED);
        } catch (Exception e){
            e.printStackTrace();
            throw new GeneralException(Code.INTERNAL_ERROR);
        }
    }
}
