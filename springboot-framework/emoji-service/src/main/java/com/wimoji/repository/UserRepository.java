package com.wimoji.repository;

import com.wimoji.repository.Entity.Emoji;
import com.wimoji.repository.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    List<Emoji> findAllByUid(String uid);
}
