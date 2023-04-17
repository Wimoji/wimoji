package com.wimoji.repository;

import com.wimoji.repository.dto.Test;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestRepository extends MongoRepository<Test, String> {
    List<Test> findAll();
}
