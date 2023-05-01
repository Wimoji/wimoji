package com.wimoji.repository.dto.request;

import lombok.Data;

@Data
public class UserReq {
    String uid;
    String password;
    String nickname;
}
