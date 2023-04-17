package com.wimoji.config;

import com.wimoji.base.GeneralException;
import com.wimoji.base.constant.Code;
import com.wimoji.base.dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(
        annotations = {RestController.class}
)
public class ExceptionHandler extends ResponseEntityExceptionHandler {
    public ExceptionHandler() {
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<Object> general(GeneralException e, WebRequest request) {
        return this.handleExceptionInternal(e, e.getErrorCode(), request);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<Object> exception(Exception e, WebRequest request) {
        return this.handleExceptionInternal(e, Code.INTERNAL_ERROR, request);
    }

    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return super.handleExceptionInternal(ex, Code.valueOf((HttpStatus)statusCode), headers, statusCode, request);
    }

    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return this.handleExceptionInternal(ex, Code.valueOf(status), headers, status, request);
    }

    private ResponseEntity<Object> handleExceptionInternal(Exception e, Code errorCode, WebRequest request) {
        return this.handleExceptionInternal(e, errorCode, HttpHeaders.EMPTY, errorCode.getHttpStatus(), request);
    }

    private ResponseEntity<Object> handleExceptionInternal(Exception e, Code errorCode, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleExceptionInternal(e, ErrorResponseDto.of(errorCode, errorCode.getMessage(e)), headers, status, request);
    }
}
