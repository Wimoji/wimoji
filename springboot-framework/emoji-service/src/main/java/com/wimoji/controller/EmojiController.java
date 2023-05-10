package com.wimoji.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wimoji.base.GeneralException;
import com.wimoji.base.constant.Code;
import com.wimoji.base.dto.DataResponseDto;
import com.wimoji.repository.Entity.User;
import com.wimoji.repository.dto.request.EmojiDeleteReq;
import com.wimoji.repository.dto.request.EmojiModifyReq;
import com.wimoji.repository.dto.request.EmojiSaveReq;
import com.wimoji.repository.dto.response.EmojiGetRes;
import com.wimoji.service.EmojiService;
import com.wimoji.service.UserServiceClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//import static com.wimoji.config.KafkaConfig.getUserByToken;

@RestController
@RequiredArgsConstructor
public class EmojiController {

    private final UserServiceClient userServiceClient;
    private final EmojiService emojiService;
//    private final ReplyingKafkaTemplate<String, String, String> template;
    private final ObjectMapper mapper;
    //    @Bean

    /**
     * 새로운 이모지 작성
     * @param emoji
     * @return
     */

    @PostMapping("/")
    public DataResponseDto<?> saveEmoji(HttpServletRequest request, @RequestBody EmojiSaveReq emoji) {
        try{
            String bearerToken = request.getHeader("Authorization");
            User user =  mapper.readValue(userServiceClient.getUser(bearerToken), User.class);

            emojiService.saveEmoji(user.getUid(), emoji);
            return DataResponseDto.empty(Code.SUCCESS_NODATA,Code.SUCCESS_NODATA.getMessage());
        } catch (JsonProcessingException je) {
            throw new GeneralException(Code.UNAUTHORIZED);
        } catch (Exception e){
            throw e;
        }
    }

    /**
     * 이모지 수정
     * @param emoji
     * @return
     */
    @PutMapping("/")
    public DataResponseDto<?> modifyEmoji(HttpServletRequest request, @RequestBody EmojiModifyReq emoji){
        try{
            String bearerToken = request.getHeader("Authorization");
            User user =  mapper.readValue(userServiceClient.getUser(bearerToken), User.class);

            emojiService.modifyEmoji(user.getUid(), emoji);
            return DataResponseDto.empty(Code.SUCCESS, Code.SUCCESS.getMessage());
        } catch (JsonProcessingException je) {
            throw new GeneralException(Code.UNAUTHORIZED);
        } catch (Exception e){
            throw e;
        }
    }

    /**
     * 이모지 삭제
     * @param emoji
     * @return
     */
    @PutMapping("/del")
    public DataResponseDto<?> deleteEmoji(HttpServletRequest request, @RequestBody EmojiDeleteReq emoji){
        try{
            String bearerToken = request.getHeader("Authorization");
            User user =  mapper.readValue(userServiceClient.getUser(bearerToken), User.class);

            emojiService.deleteEmoji(user.getUid(), emoji);
            return DataResponseDto.empty(Code.SUCCESS_NODATA, Code.SUCCESS.getMessage());
        } catch (JsonProcessingException je) {
            throw new GeneralException(Code.UNAUTHORIZED);
        } catch (Exception e){
            throw e;
        }
    }

    /**
     * 유저의 이모지 가져오기
     * @return
     */
    @GetMapping("/")
    public DataResponseDto<?> getEmojiList(HttpServletRequest request){
        try{
            String bearerToken = request.getHeader("Authorization");
            User user =  mapper.readValue(userServiceClient.getUser(bearerToken), User.class);

            List<EmojiGetRes> emojiList = emojiService.getEmojiList(user.getUid());
            if(emojiList.size() == 0) {
                return DataResponseDto.empty(Code.SUCCESS_NODATA,Code.SUCCESS_NODATA.getMessage());
            }

            return DataResponseDto.of(emojiList, Code.SUCCESS, Code.SUCCESS.getMessage());
        } catch (JsonProcessingException je) {
            throw new GeneralException(Code.UNAUTHORIZED);
        } catch (Exception e){
            throw e;
        }
    }
}
