package com.wimoji.controller;

import com.wimoji.base.constant.Code;
import com.wimoji.base.dto.DataResponseDto;
import com.wimoji.repository.dto.request.HomeReq;
import com.wimoji.repository.dto.response.HomeRes;
import com.wimoji.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final HomeService homeService;

    /**
     * 현재 위치 기반 주변 이모지 가져오기
     * @param location
     * @return
     */
    @PostMapping("/location")
    public DataResponseDto<?> getOtherEmojiList(@RequestBody HomeReq location){
        try{
            List<HomeRes> homeResList =
                    homeService.getOtherEmojiList(location.getUid(), location);
            return DataResponseDto.of(homeResList, Code.SUCCESS, Code.SUCCESS.getMessage());
        }catch (Exception e){
            throw e;
        }
    }
}
