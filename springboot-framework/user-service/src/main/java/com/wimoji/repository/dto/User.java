package com.wimoji.repository.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

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
