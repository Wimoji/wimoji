package com.wimoji.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wimoji.base.GeneralException;
import com.wimoji.base.constant.Code;
import com.wimoji.base.dto.DataResponseDto;
import com.wimoji.repository.Entity.User;
import com.wimoji.repository.dto.request.HomeReq;
import com.wimoji.repository.dto.response.HomeRes;
import com.wimoji.service.HomeService;
import com.wimoji.service.UserServiceClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//import static com.wimoji.config.KafkaConfig.getUserByToken;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final HomeService homeService;
//    private final ReplyingKafkaTemplate<String, String, String> template;
    private final UserServiceClient userServiceClient;
    private final ObjectMapper mapper;

    /**
     * 현재 위치 기반 주변 이모지 가져오기
     * @param location
     * @return
     */
    @PostMapping("/location")
    public DataResponseDto<?> getOtherEmojiList(HttpServletRequest request, @RequestBody HomeReq location){
        try{
            String bearerToken = request.getHeader("Authorization");
            User user =  mapper.readValue(userServiceClient.getUser(bearerToken), User.class);
//            User user = getUserByToken(template, bearerToken);

            List<HomeRes> homeResList =
                    homeService.getOtherEmojiList(user.getUid(), location);
            return DataResponseDto.of(homeResList, Code.SUCCESS, Code.SUCCESS.getMessage());
        } catch (JsonProcessingException je) {
            throw new GeneralException(Code.UNAUTHORIZED);
        } catch (Exception e){
            throw e;
        }
    }
}
