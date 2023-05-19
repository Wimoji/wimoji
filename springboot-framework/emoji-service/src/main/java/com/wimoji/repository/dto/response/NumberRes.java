package com.wimoji.repository.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NumberRes {
	int participant;
	int limit;
	boolean isEnter;
}