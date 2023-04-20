package com.wimoji.service;

import com.wimoji.common.JwtTokenUtil;
import com.wimoji.repository.UserRepository;
//import com.wimoji.repository.dto.Emoji;
import com.wimoji.repository.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public void makeUser(String uid, String password, String nickname) {
        User user = User.builder()
                .uid(uid)
                .nickname(nickname)
                .password(password)
                .build();

        repository.save(user);
    }

    public HashMap<String, String> loginUser(String id, String password) {
        Query query = new Query();
        Criteria criteria = new Criteria();

        query.addCriteria(criteria.andOperator(
                Criteria.where("uid").is(id),
                Criteria.where("password").is(password)
        ));

        //로그인 상태 변경
        User user = mongoTemplate.findAndModify(
                query,
                Update.update("login", true),
                User.class
        );

        if (user != null) {
            //로그인 성공, 토큰 발급
            String accessToken = JwtTokenUtil.makeAccessToken(user.getUid(), user.getNickname());
            String refreshToken = JwtTokenUtil.makeRefreshToken(user.getUid(), user.getNickname());

            HashMap<String, String> result = new HashMap<>();
            result.put("accessToken", accessToken);
            result.put("refreshToken", refreshToken);

            return result;
        }
        return null; //유저가 없을 시 null return
    }
}
