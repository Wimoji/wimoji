package com.wimoji.service;

import com.wimoji.base.GeneralException;
import com.wimoji.base.constant.Code;
import com.wimoji.repository.Entity.Emoji;
import com.wimoji.repository.Entity.User;
import com.wimoji.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Criteria criteria  = Criteria.where("uid").is(uid);
        Update update = new Update();

        Emoji emoji = Emoji.builder()
                .eid(eid)
                .content(content)
                .dongCode(dongCode)
                .latitude(latitude)
                .longitude(longitude)
                .dongCode(dongCode)
                .build();

        update.push("emoji", emoji);
        mongoTemplate.updateFirst(Query.query(criteria), update, User.class);
    }


}
