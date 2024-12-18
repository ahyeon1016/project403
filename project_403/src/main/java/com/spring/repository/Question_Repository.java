package com.spring.repository;

import java.util.ArrayList;

import com.spring.domain.Question;

public interface Question_Repository {
	void addMCQ(Question question);
	void addSAQ(Question question);
	void addCP(Question question);
	ArrayList<Question> getQuestionAll();
	ArrayList<Question> getQuestionsBySubCode(String sub_code);
	Question getQuestionBySerial(String question_serial);
	void updateQuestionCount(String question_serial, int question_count);
	void updateQuestion(Question question);
	void visibleQuestion(String question_serial);
}
