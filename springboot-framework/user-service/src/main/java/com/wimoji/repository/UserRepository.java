package com.wimoji.repository;

import com.wimoji.repository.dto.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    public void save(UserEntity userEntity) {
        mongoTemplate.save(userEntity);
    }

    public UserEntity findById(String uid) {
        return mongoTemplate.findById(uid, UserEntity.class);
    }

    /**
     * 로그인, 로그아웃
     *
     * @param id
     * @param password
     * @param isLogin
     * @return
     */
    public UserEntity findAndModify(String id, String password, boolean isLogin) {
        Query query = new Query();
        Criteria criteria = new Criteria();

        if (password != null) { //로그인 : 아이디, 패스워드
            query.addCriteria(criteria.andOperator(
                    Criteria.where("uid").is(id),
                    Criteria.where("password").is(password)
            ));
        } else { //로그아웃 : 아이디
            query.addCriteria(criteria.andOperator(
                    Criteria.where("uid").is(id)
            ));
        }
        //로그인 상태 변경
        UserEntity userEntity = mongoTemplate.findAndModify(
                query,
                Update.update("login", isLogin),
                UserEntity.class
        );
        return userEntity;
    }

    public UserEntity findAndRemove(String id) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        query.addCriteria(criteria.andOperator(Criteria.where("uid").is(id)));

        UserEntity userEntity = mongoTemplate.findAndRemove(query, UserEntity.class);
        return userEntity;
    }

    public void makeChat(String uid, String rid) {
        Query query = new Query();
        query.addCriteria(Criteria.where("uid").is(uid));
        Update update = new Update().addToSet("chatList", rid);

        mongoTemplate.updateFirst(query, update, UserEntity.class);
    }

    public void removeChat(String uid, String rid) {
        Query query = new Query();
        query.addCriteria(Criteria.where("uid").is(uid));
        Update update = new Update().pull("chatList.", rid);
        mongoTemplate.updateFirst(query, update, UserEntity.class);
    }
}
