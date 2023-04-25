package com.wimoji.base.dto;

import com.wimoji.base.constant.Code;
import lombok.Getter;

@Getter
public class DataResponseDto<T> extends ResponseDto {

    private final T data;

    private DataResponseDto(T data) {
        super(true, Code.OK.getCode(), Code.OK.getMessage());
        this.data = data;
    }
    private DataResponseDto(T data, int code, String message) {
        super(true, code, message);
        this.data = data;
    }

    private DataResponseDto(T data, String message) {
        super(true, Code.OK.getCode(), message);
        this.data = data;
    }

    public static <T> DataResponseDto<T> of(T data) {
        return new DataResponseDto<>(data);
    }

    public static <T> DataResponseDto<T> of(T data, String message) {
        return new DataResponseDto<>(data, message);
    }

    public static <T> DataResponseDto<T> empty(int code, String message) {
        return new DataResponseDto<>(null, code, message);
    }
    public static <T> DataResponseDto<T> of(T data, int code, String message){return new DataResponseDto<>(data, code, message);}
}
