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
import com.wimoji.repository.dto.response.NumberRes;
import com.wimoji.service.ChatServiceClient;
import com.wimoji.service.EmojiService;
import com.wimoji.service.UserServiceClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmojiController {

    private final UserServiceClient userServiceClient;
    private final ChatServiceClient chatServiceClient;
    private final EmojiService emojiService;
    private final ObjectMapper mapper;

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
            throw new GeneralException(Code.INTERNAL_ERROR);
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
            throw new GeneralException(Code.INTERNAL_ERROR);
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
            throw new GeneralException(Code.INTERNAL_ERROR);
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

            for(EmojiGetRes emojiGetRes : emojiList) {
                NumberRes numberRes = mapper.readValue(chatServiceClient.getNumber(emojiGetRes.getRid()), NumberRes.class);
                emojiGetRes.setParticipant(numberRes.getParticipant());
                emojiGetRes.setLimit(numberRes.getLimit());
                emojiGetRes.setEnter(numberRes.isEnter());
            }

            return DataResponseDto.of(emojiList, Code.SUCCESS, Code.SUCCESS.getMessage());
        } catch (JsonProcessingException je) {
            throw new GeneralException(Code.UNAUTHORIZED);
        } catch (Exception e){
            throw new GeneralException(Code.INTERNAL_ERROR);
        }
    }
}
