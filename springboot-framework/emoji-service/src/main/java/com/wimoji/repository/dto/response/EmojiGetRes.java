package com.wimoji.repository.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmojiGetRes {
    String eid;
    String rid;
    String content;
    String latitude;
    String longitude;
    String dongCode;
}
