package com.wimoji.repository.Entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Emoji {
    String eid;
    String content;
    String latitude;
    String longitude;
    String dongCode;
}
