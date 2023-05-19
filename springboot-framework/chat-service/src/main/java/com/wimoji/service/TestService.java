package com.wimoji.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wimoji.repository.TestRepository;
import com.wimoji.repository.dto.entity.Test;

@Service
public class TestService {
	@Autowired
	private TestRepository repository;

	public List<Test> getAll() {
		return this.repository.findAll();
	}
}