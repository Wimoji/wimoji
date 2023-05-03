package com.wimoji.repository.dto.request;

import lombok.Data;

@Data
public class EmojiSaveReq {
    String eid;
    String rid;
    String content;
    String latitude;
    String longitude;
    String dongCode;
}
