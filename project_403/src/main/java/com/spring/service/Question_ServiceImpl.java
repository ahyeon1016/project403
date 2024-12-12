package com.spring.service;

import java.util.ArrayList;

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

	@Override
	public void addSAQ(Question question) {
		System.out.println("서비스 | addSAQ() 호출");
		questionRepository.addSAQ(question);
	}

	@Override
	public void addCP(Question question) {
		System.out.println("서비스 | addCP() 호출");
		questionRepository.addCP(question);
	}

	@Override
	public ArrayList<Question> getQuestionAll() {
		System.out.println("서비스 | getQuestionAll() 호출");
		return questionRepository.getQuestionAll();
	}

	@Override
	public ArrayList<Question> getQuestionsBySubCode(String sub_code) {
		System.out.println("서비스 | getQuestionsBySubCode() 호출");
		return questionRepository.getQuestionsBySubCode(sub_code);
	}

	@Override
	public Question getQuestionBySerial(String question_serial) {
		System.out.println("서비스 | getQuestionBySerial() 호출");
		return questionRepository.getQuestionBySerial(question_serial);
	}
	
}
