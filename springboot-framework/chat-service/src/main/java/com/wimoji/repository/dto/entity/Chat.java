package com.wimoji.repository.dto.entity;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Chat {
	String rid;
	String uid;
	String nickname;
	String content;
	LocalDateTime mTime;
}