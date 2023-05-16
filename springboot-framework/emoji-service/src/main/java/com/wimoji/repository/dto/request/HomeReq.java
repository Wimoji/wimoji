package com.wimoji.repository.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HomeReq {
    String latitude;
    String longitude;
    String dongCode;
}
