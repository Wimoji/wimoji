package com.wimoji.repository.dto.request;

import lombok.Data;

@Data
public class EmojiSaveReqDto {
    String uid;//후에 uid는 헤더로!
    String eId;
    String content;
    String latitude;
    String longitude;
    String dongCode;
}
