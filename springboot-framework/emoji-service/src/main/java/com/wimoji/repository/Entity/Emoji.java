package com.wimoji.repository.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Emoji {
    String eid;
    String content;
    String latitude;
    String longitude;
    String dongCode;
}
