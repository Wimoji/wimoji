package com.wimoji.base.dto;

import com.wimoji.base.constant.Code;

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

    public Boolean getSuccess() {
        return this.success;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public String toString() {
        Boolean var10000 = this.getSuccess();
        return "ResponseDto(success=" + var10000 + ", code=" + this.getCode() + ", message=" + this.getMessage() + ")";
    }

    public ResponseDto(final Boolean success, final Integer code, final String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
