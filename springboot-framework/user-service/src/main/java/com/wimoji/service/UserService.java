package com.wimoji.service;

import com.wimoji.repository.UserRepository;
import com.wimoji.repository.dto.Emoji;
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

    public void makeUser(String uid, String nickname, String password) {
        User user = User.builder()
                .uid(uid)
                .nickname(nickname)
                .password(password)
                .build();

        repository.save(user);
    }

    public void loginUser(String id, String password) {
        Query query = new Query();
        Criteria criteria = new Criteria();

        query.addCriteria(criteria.andOperator(Criteria.where("uid").is(id)));
        query.addCriteria(criteria.andOperator(Criteria.where("password").is(password)));

        User user = mongoTemplate.findAndModify(
                query,
                Update.update("login", true),
                User.class
        );
    }
}
