package com.wimoji.repository.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NumberRes {
	int participant;
	int limit;
	boolean isEnter;
}