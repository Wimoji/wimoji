package com.wimoji.controller;

import com.wimoji.service.EmojiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wimoji.base.constant.Code;

@RestController
@RequestMapping("/emoji-service")
public class EmojiController {
    @Autowired
    private EmojiService emojiService;

    /**
     * 이모지 작성
     *
     *
     */
//    @PostMapping("/")
//    public saveEmoji(){
//        return ;
//    }

}
