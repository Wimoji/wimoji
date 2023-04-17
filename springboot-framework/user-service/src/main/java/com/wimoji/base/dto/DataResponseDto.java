package com.wimoji.base.dto;

import com.wimoji.base.constant.Code;

public class DataResponseDto<T> extends ResponseDto {
    private final T data;

    private DataResponseDto(T data) {
        super(true, Code.OK.getCode(), Code.OK.getMessage());
        this.data = data;
    }

    private DataResponseDto(T data, String message) {
        super(true, Code.OK.getCode(), message);
        this.data = data;
    }

    public static <T> DataResponseDto<T> of(T data) {
        return new DataResponseDto(data);
    }

    public static <T> DataResponseDto<T> of(T data, String message) {
        return new DataResponseDto(data, message);
    }

    public static <T> DataResponseDto<T> empty() {
        return new DataResponseDto((Object)null);
    }

    public T getData() {
        return this.data;
    }
}
