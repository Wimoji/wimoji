package com.wimoji.repository.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomeRes {
    String uid;
    String eid;
    String rid;
    String title;
    String latitude;
    String longitude;
    String dongCode;
    double dis;
}
