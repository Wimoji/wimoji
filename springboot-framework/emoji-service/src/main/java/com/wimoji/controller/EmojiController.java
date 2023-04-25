package com.wimoji.controller;

import com.wimoji.base.dto.DataResponseDto;
import com.wimoji.repository.Entity.Emoji;
import com.wimoji.repository.dto.request.EmojiDeleteReq;
import com.wimoji.repository.dto.request.EmojiModifyReq;
import com.wimoji.repository.dto.request.EmojiSaveReq;
import com.wimoji.repository.dto.response.EmojiGetRes;
import com.wimoji.service.EmojiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
            return DataResponseDto.empty(201,"SUCCESS");
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
            return DataResponseDto.empty(200, "SUCCESS");
        }catch (Exception e){
            throw e;
        }
    }

    /**
     * 이모지 삭제
     * @param emoji
     * @return
     */
    @PutMapping("/del")
    public DataResponseDto<?> deleteEmoji(@RequestBody EmojiDeleteReq emoji){
        try{
            emojiService.deleteEmoji(emoji.getUid(), emoji.getOrder(), emoji.getEid());
            return DataResponseDto.empty(201, "SUCCESS");
        }catch (Exception e){
            throw e;
        }
    }

    @GetMapping("/")
    public DataResponseDto<?> getEmojiList(){
        try{
            String uid = "ssafy"; // 후에는 header로 할 예정
            List<EmojiGetRes> emojiList = emojiService.getEmojiList(uid);
            return DataResponseDto.of(emojiList,200,"SUCCESS");
        } catch (Exception e){
            throw e;
        }
    }



}
