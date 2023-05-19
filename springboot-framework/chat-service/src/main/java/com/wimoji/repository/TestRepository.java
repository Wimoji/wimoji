package com.wimoji.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.wimoji.repository.dto.entity.Test;

public interface TestRepository extends MongoRepository<Test, String> {
	List<Test> findAll();
}