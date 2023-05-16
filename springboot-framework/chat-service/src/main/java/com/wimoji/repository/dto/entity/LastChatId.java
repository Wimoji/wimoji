package com.wimoji.repository.dto.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LastChatId implements Serializable {
	String uid;
	String rid;
}