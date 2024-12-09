package com.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.domain.Question;
import com.spring.repository.Question_Repository;

@Service
public class Question_ServiceImpl implements Question_Service{
	
	@Autowired
	Question_Repository questionRepository;
	
	@Override
	public void addMCQ(Question question) {
		System.out.println("서비스 | addMCQ() 호출");
		questionRepository.addMCQ(question);
	}
}
