package com.wimoji.repository.dto.request;

import lombok.Data;

@Data
public class EmojiSaveReqDto {
    String uid;//후에 uid는 헤더로!
    String eid;
    String content;
    String latitude;
    String longitude;
    String dongCode;
}
