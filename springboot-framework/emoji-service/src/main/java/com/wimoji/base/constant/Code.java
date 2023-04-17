
package com.wimoji.base.constant;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;
import org.springframework.http.HttpStatus;

public enum Code {
    OK(0, HttpStatus.OK, "Ok"),
    BAD_REQUEST(10000, HttpStatus.BAD_REQUEST, "Bad request"),
    VALIDATION_ERROR(10001, HttpStatus.BAD_REQUEST, "Validation error"),
    NOT_FOUND(10002, HttpStatus.NOT_FOUND, "Requested resource is not found"),
    INTERNAL_ERROR(20000, HttpStatus.INTERNAL_SERVER_ERROR, "Internal error"),
    DATA_ACCESS_ERROR(20001, HttpStatus.INTERNAL_SERVER_ERROR, "Data access error"),
    UNAUTHORIZED(40000, HttpStatus.UNAUTHORIZED, "User unauthorized");

    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;

    public String getMessage(Throwable e) {
        String var10001 = this.getMessage();
        return this.getMessage(var10001 + " - " + e.getMessage());
    }

    public String getMessage(String message) {
        return (String)Optional.ofNullable(message).filter(Predicate.not(String::isBlank)).orElse(this.getMessage());
    }

    public static Code valueOf(HttpStatus httpStatus) {
        if (httpStatus == null) {
            throw new GeneralException("HttpStatus is null.");
        } else {
            return (Code)Arrays.stream(values()).filter((errorCode) -> {
                return errorCode.getHttpStatus() == httpStatus;
            }).findFirst().orElseGet(() -> {
                if (httpStatus.is4xxClientError()) {
                    return BAD_REQUEST;
                } else {
                    return httpStatus.is5xxServerError() ? INTERNAL_ERROR : OK;
                }
            });
        }
    }

    public String toString() {
        return String.format("%s (%d)", this.name(), this.getCode());
    }

    public Integer getCode() {
        return this.code;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    public String getMessage() {
        return this.message;
    }

    private Code(final Integer code, final HttpStatus httpStatus, final String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
