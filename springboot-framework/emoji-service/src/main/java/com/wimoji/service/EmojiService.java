package com.wimoji.service;

import com.wimoji.base.GeneralException;
import com.wimoji.base.constant.Code;
import com.wimoji.repository.Entity.Emoji;
import com.wimoji.repository.Entity.User;
import com.wimoji.repository.UserRepository;
import com.wimoji.repository.dto.request.EmojiDeleteReq;
import com.wimoji.repository.dto.request.EmojiModifyReq;
import com.wimoji.repository.dto.request.EmojiSaveReq;
import com.wimoji.repository.dto.response.EmojiGetRes;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EmojiService {
    private final UserRepository userRepository;

    /**
     * 새로운 이모지 저장
     * @param uid
     * @param emojiReq
     */
    public void saveEmoji(String uid, EmojiSaveReq emojiReq){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Emoji emoji = mapper.map(emojiReq, Emoji.class);
        userRepository.saveEmoji(uid, emoji);
    }

    /**
     * 이모지 수정
     * @param uid
     * @param emojiReq
     */
    public void modifyEmoji(String uid, EmojiModifyReq emojiReq){
        //user 찾기
        User document = userRepository.findUserByUid(uid);

        //index가 emoji list size내에 있어야 update
        int intOrder = Integer.parseInt(emojiReq.getOrder());
        if(intOrder >= 0 && intOrder<document.getEmoji().size()) {
            if(document.getEmoji().get(intOrder).getEid().equals(emojiReq.getEid())){
                userRepository.updateEmoji(uid, emojiReq.getOrder(), emojiReq.getContent());
            }
            else
                throw new GeneralException(Code.NO_EMOJI);
        }
        else
            throw new GeneralException(Code.NO_EMOJI);
    }

    /**
     * 이모지 삭제
     * @param uid
     * @param emojiReq
     */
    public void deleteEmoji(String uid, EmojiDeleteReq emojiReq){
        User document = userRepository.findUserByUid(uid);

        //index가 emoji list size내에 있어야 delete
        int intOrder = Integer.parseInt(emojiReq.getOrder());
        if(intOrder >= 0 && intOrder<document.getEmoji().size()) {
            if(document.getEmoji().get(intOrder).getEid().equals(emojiReq.getEid())){
                userRepository.removeEmoji(uid, intOrder);
            }
            else
                throw new GeneralException(Code.NO_EMOJI);
        }
        else
            throw new GeneralException(Code.NO_EMOJI);
    }

    /**
     * 이모지 리스트 가져오기
     * @param uid
     * @return
     */
    public List<EmojiGetRes> getEmojiList(String uid){
        List<Emoji> emojiList = userRepository.getEmojiList(uid);

        //반환할 리스트
        List<EmojiGetRes> emojiGetList = new ArrayList<>();
        if(emojiList != null) {
            for (int i = 0; i < emojiList.size(); i++) {
                Emoji emoji = emojiList.get(i);

                ModelMapper mapper = new ModelMapper();
                mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
                EmojiGetRes emojiGetRes = mapper.map(emoji, EmojiGetRes.class);
                emojiGetList.add(emojiGetRes);
            }
        }
        return emojiGetList;
    }


}
