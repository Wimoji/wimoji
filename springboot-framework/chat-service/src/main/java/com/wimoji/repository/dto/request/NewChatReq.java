package com.wimoji.repository.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewChatReq {
	String rid;
	String uid;
	int startIdx;
	int endIdx;
}
