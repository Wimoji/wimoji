package com.wimoji.repository.dto.request;

import lombok.Data;

@Data
public class UserReqDto {
    String uid;
    String password;
    String nickname;
}
