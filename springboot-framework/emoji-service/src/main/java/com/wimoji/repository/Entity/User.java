package com.wimoji.repository.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    @Id
    String uid;
    String nickname;
    String password;
    List<Emoji> emoji;
    boolean login = false;
}

