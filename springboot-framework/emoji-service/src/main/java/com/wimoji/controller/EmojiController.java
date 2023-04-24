package com.wimoji.controller;

import com.wimoji.base.dto.DataResponseDto;
import com.wimoji.repository.dto.request.EmojiDeleteReq;
import com.wimoji.repository.dto.request.EmojiModifyReq;
import com.wimoji.repository.dto.request.EmojiSaveReq;
import com.wimoji.service.EmojiService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public DataResponseDto<?> saveEmoji(@RequestBody EmojiSaveReq emoji){
        try{
            emojiService.saveEmoji(emoji.getUid(), emoji.getEid(), emoji.getContent(), emoji.getLatitude(), emoji.getLongitude(), emoji.getDongCode());
            return DataResponseDto.empty();
        }catch (Exception e){
            throw e;
        }
    }

    /**
     * 이모지 수정
     * @param emoji
     * @return
     */
    @PutMapping("/")
    public DataResponseDto<?> modifyEmoji(@RequestBody EmojiModifyReq emoji){
        try{
            emojiService.modifyEmoji(emoji.getUid(), emoji.getOrder(), emoji.getEid(), emoji.getContent());
            return DataResponseDto.empty();
        }catch (Exception e){
            throw e;
        }
    }

    @PutMapping("/del")
    public DataResponseDto<?> deleteEmoji(@RequestBody EmojiDeleteReq emoji){
        try{
            emojiService.deleteEmoji(emoji.getUid(), emoji.getOrder(), emoji.getEid());
            return DataResponseDto.empty();
        }catch (Exception e){
            throw e;
        }
    }



}
