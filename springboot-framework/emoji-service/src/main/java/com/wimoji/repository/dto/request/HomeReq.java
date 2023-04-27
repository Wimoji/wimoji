package com.wimoji.repository.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HomeReq {
    String uid; //후에는 header로 처리
    String latitude;
    String longitude;
    String dongCode;
}
