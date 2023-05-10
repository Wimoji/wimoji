package com.wimoji.repository.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Chat {
	String rid;
	String uid;
	String nickname;
	String content;
}