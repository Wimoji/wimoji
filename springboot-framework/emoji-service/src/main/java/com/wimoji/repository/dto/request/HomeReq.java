package com.wimoji.repository.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeReq {
    String latitude;
    String longitude;
    String dongCode;
}
