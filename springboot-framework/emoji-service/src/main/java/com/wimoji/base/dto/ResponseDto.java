package com.wimoji.base.dto;

import com.wimoji.base.constant.Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class ResponseDto {

    private final Boolean success;
    private final Integer code;
    private final String message;

    public static ResponseDto of(Boolean success, Code code) {
        return new ResponseDto(success, code.getCode(), code.getMessage());
    }

    public static ResponseDto of(Boolean success, Code errorCode, Exception e) {
        return new ResponseDto(success, errorCode.getCode(), errorCode.getMessage(e));
    }

    public static ResponseDto of(Boolean success, Code errorCode, String message) {
        return new ResponseDto(success, errorCode.getCode(), errorCode.getMessage(message));
    }
}