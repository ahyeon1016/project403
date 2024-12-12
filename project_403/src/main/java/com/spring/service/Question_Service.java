package com.spring.service;

import java.util.ArrayList;

import com.spring.domain.Question;

public interface Question_Service {
	void addMCQ(Question question);
	void addSAQ(Question question);
	void addCP(Question question);
	ArrayList<Question> getQuestionAll();
	ArrayList<Question> getQuestion(String sub_code);
}
