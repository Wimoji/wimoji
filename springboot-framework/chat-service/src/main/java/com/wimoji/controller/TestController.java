package com.wimoji.controller;

import com.wimoji.base.GeneralException;
import com.wimoji.base.constant.Code;
import com.wimoji.repository.dto.Test;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private final TestService service;

    @GetMapping({"/test"})
    public ResponseEntity<?> Test() {
        List<Test> list = this.service.getAll();

        try {
            if (list.size() == 0) {
                throw new GeneralException(Code.BAD_REQUEST, "zero!");
            } else {
                return new ResponseEntity(list, HttpStatus.OK);
            }
        } catch (Exception var3) {
            throw var3;
        }
    }

    public TestController(final TestService service) {
        this.service = service;
    }
}
