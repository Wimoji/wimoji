package com.wimoji.repository;

import com.wimoji.repository.Entity.Emoji;
import com.wimoji.repository.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * HomeService - 본인을 제외한 현재 있는 모든 유저 중 로그인한 사람들 조회
     * @param uid
     * @return
     */
    public List<User> findLoginedUser(String uid) {
        //select * from user where login == true and uid = uid;
        Criteria criteria = Criteria.where("login").is(true);
//                .andOperator(Criteria.where("uid").ne(uid));
        Query query = new Query(criteria);
        List<User> userList = mongoTemplate.find(query, User.class);
        return userList;
    }

    /**
     * EmojiService - 이모지 저장
     * @param uid
     * @param emoji
     */
    public void saveEmoji(String uid, Emoji emoji){
        //select * from user where uid=uid;
        Criteria criteria  = Criteria.where("uid").is(uid);
        Update update = new Update();
        update.push("emoji", emoji);
        mongoTemplate.updateFirst(Query.query(criteria), update, User.class);
    }

    /**
     * EmojiService - uid로 user 찾기
     * @param uid
     * @return
     */

    public User findUserByUid(String uid){
        //select * from user where uid=uid;
        Criteria criteria = Criteria.where("uid").is(uid);
        Query query = new Query(criteria);
        User document = mongoTemplate.findOne(query, User.class);
        return document;
    }

    /**
     * EmojiService - Emoji 수정
     * @param uid
     * @param order
     * @param title
     */
    public void updateEmoji(String uid, String order, String title){
        //select * from user where uid=uid;
        Criteria criteria = Criteria.where("uid").is(uid);
        Query query = new Query(criteria);

        Update update = new Update().set("emoji" + "." + order + ".title", title);
//        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(false);
//        mongoTemplate.findAndModify(query, update, options, User.class);
        mongoTemplate.findAndModify(query, update, User.class);
    }

    /**
     * EmojiService - Emoji 삭제
     * @param uid
     * @param intOrder
     */
    public void removeEmoji(String uid, int intOrder){
        User document = findUserByUid(uid);
        document.getEmoji().remove(intOrder);
        mongoTemplate.save(document);
    }

    /**
     * EmojiService - 유저의 emoji list 반환
     * @param uid
     * @return
     */
    public List<Emoji> getEmojiList(String uid){
        Criteria criteria = Criteria.where("uid").is(uid);
        Query query = new Query(criteria);
        User user = mongoTemplate.findOne(query, User.class);
        List<Emoji> emojiList = user.getEmoji();
        return emojiList;
    }
}
