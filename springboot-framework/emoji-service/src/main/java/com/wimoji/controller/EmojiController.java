package com.wimoji.controller;

import com.wimoji.base.constant.Code;
import com.wimoji.base.dto.DataResponseDto;
import com.wimoji.repository.Entity.User;
import com.wimoji.repository.dto.request.EmojiDeleteReq;
import com.wimoji.repository.dto.request.EmojiModifyReq;
import com.wimoji.repository.dto.request.EmojiSaveReq;
import com.wimoji.repository.dto.response.EmojiGetRes;
import com.wimoji.service.EmojiService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.wimoji.config.KafkaConfig.getUserByToken;

@RestController
@RequiredArgsConstructor
public class EmojiController {
    private final EmojiService emojiService;
    private final ReplyingKafkaTemplate<String, String, String> template;
    //    @Bean

    /**
     * 새로운 이모지 작성
     * @param emoji
     * @return
     */

    @PostMapping("/")
    public DataResponseDto<?> saveEmoji(HttpServletRequest request, @RequestBody EmojiSaveReq emoji){
        try{

            String bearerToken = request.getHeader("Authorization");

            User user = getUserByToken(template, bearerToken);
            emojiService.saveEmoji(user.getUid(), emoji);
            return DataResponseDto.empty(Code.SUCCESS_NODATA,Code.SUCCESS_NODATA.getMessage());
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
            emojiService.modifyEmoji(emoji.getUid(), emoji);
            return DataResponseDto.empty(Code.SUCCESS, Code.SUCCESS.getMessage());
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
            emojiService.deleteEmoji(emoji.getUid(), emoji);
            return DataResponseDto.empty(Code.SUCCESS_NODATA, Code.SUCCESS.getMessage());
        }catch (Exception e){
            throw e;
        }
        }

    /**
     * 유저의 이모지 가져오기
     * @return
     */
    @GetMapping("/")
        public DataResponseDto<?> getEmojiList(){
            try{
                String uid = "ssafy"; // 후에는 header로 할 예정
                List<EmojiGetRes> emojiList = emojiService.getEmojiList(uid);
            return DataResponseDto.of(emojiList, Code.SUCCESS, Code.SUCCESS.getMessage());
        } catch (Exception e){
            throw e;
        }
    }
}
