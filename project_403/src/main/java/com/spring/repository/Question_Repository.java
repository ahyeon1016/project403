package com.spring.repository;

import java.util.ArrayList;

import com.spring.domain.Question;

public interface Question_Repository {
	void addMCQ(Question question);
	void addSAQ(Question question);
	void addCP(Question question);
	ArrayList<Question> getQuestionAll();
	ArrayList<Question> getQuestion(String sub_code);
}
