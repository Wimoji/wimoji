package com.wimoji.controller;

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
    @PostMapping("/location")
    public DataResponseDto<?> getOtherEmojiList(@RequestBody HomeReq location){
        try{
            List<HomeRes> homeResList =
                    homeService.getOtherEmojiList(location.getUid(), location.getLatitude(), location.getLongitude(), location.getDongCode());
            return DataResponseDto.of(homeResList, 200, "SUCCESS");
        }catch (Exception e){
            throw e;
        }
    }
}
