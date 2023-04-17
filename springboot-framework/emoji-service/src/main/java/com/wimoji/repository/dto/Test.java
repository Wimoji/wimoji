package com.wimoji.repository.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "testdb")

@Data
public class Test {
    @Id
    private String _id;
    private String title;
    private String content;
}
