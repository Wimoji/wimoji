package com.wimoji.repository.Entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "user")
@Builder
@Data
public class User {
    @Id
    String uid;
    String nickname;
    String password;
    List<Emoji> emoji;
    boolean login = false;
}

