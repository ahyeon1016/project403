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
	public void addMCQ(Question question, int mem_serial) {
		System.out.println("서비스 | addMCQ() 호출");
		questionRepository.addMCQ(question, mem_serial);
	}

	@Override
	public void addSAQ(Question question, int mem_serial) {
		System.out.println("서비스 | addSAQ() 호출");
		questionRepository.addSAQ(question, mem_serial);
	}

	@Override
	public void addCP(Question question, int mem_serial) {
		System.out.println("서비스 | addCP() 호출");
		questionRepository.addCP(question, mem_serial);
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

	@Override
	public void updateQuestionCount(String question_serial, int question_count) {
		System.out.println("서비스 | updateQuestionCount() 호출");
		questionRepository.updateQuestionCount(question_serial, question_count);
	}

	@Override
	public void updateQuestion(Question question) {
		System.out.println("서비스 | updateQuestion() 호출");
		questionRepository.updateQuestion(question);
	}

	@Override
	public void visibleQuestion(String question_serial) {
		System.out.println("서비스 | visibleQuestion() 호출");
		questionRepository.visibleQuestion(question_serial);
	}

	


}
