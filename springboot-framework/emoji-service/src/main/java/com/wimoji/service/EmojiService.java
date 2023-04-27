package com.wimoji.service;

import com.mongodb.client.result.UpdateResult;
import com.wimoji.base.GeneralException;
import com.wimoji.base.constant.Code;
import com.wimoji.repository.Entity.Emoji;
import com.wimoji.repository.Entity.User;
import com.wimoji.repository.UserRepository;
import com.wimoji.repository.dto.response.EmojiGetRes;
import com.wimoji.repository.dto.response.HomeRes;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EmojiService {
    private final UserRepository userRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 새로운 이모지 저장
     * @param uid
     * @param eid
     * @param content
     * @param latitude
     * @param longitude
     * @param dongCode
     */
    public void saveEmoji(String uid, String eid, String content, String latitude, String longitude, String dongCode){
        Emoji emoji = Emoji.builder()
                .eid(eid)
                .content(content)
                .latitude(latitude)
                .longitude(longitude)
                .dongCode(dongCode)
                .build();

        userRepository.saveEmoji(uid, emoji);
    }

    /**
     * 이모지 수정
     * @param uid
     * @param order
     * @param content
     */
    public void modifyEmoji(String uid, String order, String eid, String content){
        //user 찾기
        User document = userRepository.findUserByUid(uid);

        //index가 emoji list size내에 있어야 update
        int intOrder = Integer.parseInt(order);
        if(intOrder >= 0 && intOrder<document.getEmoji().size()) {
            if(document.getEmoji().get(intOrder).getEid().equals(eid)){
                userRepository.updateEmoji(uid, order, content);
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
     * @param order
     * @param eid
     */
    public void deleteEmoji(String uid, String order, String eid){
        User document = userRepository.findUserByUid(uid);

        //index가 emoji list size내에 있어야 delete
        int intOrder = Integer.parseInt(order);
        if(intOrder >= 0 && intOrder<document.getEmoji().size()) {
            if(document.getEmoji().get(intOrder).getEid().equals(eid)){
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
