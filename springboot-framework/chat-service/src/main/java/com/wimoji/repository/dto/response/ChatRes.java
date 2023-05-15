package com.wimoji.repository.dto.response;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatRes {
	String rid;
	String nickname;
	String content;
	String flag; // 1이 본인 2가 상대 3이 전체 그 외는 sessionId
	LocalDateTime mTime;
}