package com.wimoji.repository.dto.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "testdb")
public class Test {
	@Id
	private String _id;
	private String title;
	private String content;
}