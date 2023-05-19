package com.wimoji.repository.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmojiDeleteReq {
    String order; // 이모지 순서, 생성일 기준으로 오름차순
    String eid;
}
