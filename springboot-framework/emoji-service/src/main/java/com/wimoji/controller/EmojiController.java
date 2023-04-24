package com.wimoji.controller;

import com.wimoji.base.dto.DataResponseDto;
import com.wimoji.repository.dto.request.EmojiSaveReqDto;
import com.wimoji.service.EmojiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wimoji.base.constant.Code;

@RestController
//@RequestMapping("/emoji-service")
@RequiredArgsConstructor
public class EmojiController {
    private final EmojiService emojiService;

    /**
     * 새로운 이모지 작성
     * @param emoji
     * @return
     */
    @PostMapping("/")
    public DataResponseDto<?> saveEmoji(@RequestBody EmojiSaveReqDto emoji){
        try{
            emojiService.saveEmoji(emoji.getUid(), emoji.getEid(), emoji.getContent(), emoji.getLatitude(), emoji.getLongitude(), emoji.getDongCode());
            return DataResponseDto.empty();
        }catch (Exception e){
            throw e;
        }
    }

}
