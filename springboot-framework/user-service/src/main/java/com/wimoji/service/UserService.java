package com.wimoji.service;

import com.wimoji.base.GeneralException;
import com.wimoji.base.constant.Code;
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
        User findUser = mongoTemplate.findById(uid, User.class);

        if(findUser != null){
            throw new GeneralException(Code.ALREADY_USER);
        }

        User user = User.builder()
                .uid(uid)
                .nickname(nickname)
                .password(password)
                .build();

        repository.save(user);
    }

    /**
     * 로그인 상태 변경 및 토큰 발급
     * @param id
     * @param password
     * @return
     */
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

        if (user == null) {
            throw new GeneralException(Code.NO_USER);
        }

        //로그인 성공, 토큰 발급
        String accessToken = JwtTokenUtil.makeAccessToken(user.getUid(), user.getNickname());
        String refreshToken = JwtTokenUtil.makeRefreshToken(user.getUid(), user.getNickname());

        HashMap<String, String> result = new HashMap<>();
        result.put("accessToken", accessToken);
        result.put("refreshToken", refreshToken);

        return result;
    }

    /**
     * 로그아웃 상태 변경
     * @param id
     */
    public void logoutUser(String id){
        Query query = new Query();
        Criteria criteria = new Criteria();

        query.addCriteria(criteria.andOperator(
                Criteria.where("uid").is(id)
        ));

        //로그아웃 상태로 변경
        User user = mongoTemplate.findAndModify(
                query,
                Update.update("login", false),
                User.class
        );

        if(user == null){
            throw new GeneralException(Code.NO_USER);
        }
    }

    /**
     * 회원 탈퇴
     * @param id
     */
    public void deleteUser(String id){
        Query query = new Query();
        Criteria criteria = new Criteria();

        query.addCriteria(criteria.andOperator(
                Criteria.where("uid").is(id)
        ));

        User user = mongoTemplate.findAndRemove(
                query,
                User.class
        );
    }

}
