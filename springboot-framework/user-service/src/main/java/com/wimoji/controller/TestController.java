package com.wimoji.controller;

import com.wimoji.base.GeneralException;
import com.wimoji.base.constant.Code;
import com.wimoji.repository.dto.Test;

import java.util.List;

import com.wimoji.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final TestService service;

    @GetMapping({"/test"})
    public ResponseEntity<?> Test() {
        List<Test> list = this.service.getAll();

        try {
            if (list.size() == 0) {
                throw new GeneralException(Code.BAD_REQUEST, "zero!");
                //{"success":false,"code":10000,"message":"Bad request - zero!"}
            } else {
                return new ResponseEntity(list, HttpStatus.OK);
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
