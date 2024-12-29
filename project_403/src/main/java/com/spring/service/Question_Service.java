package com.spring.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.spring.domain.Question;

public interface Question_Service {
	void addMCQ(Question question, int mem_serial);
	void addSAQ(Question question, int mem_serial);
	void addCP(Question question, int mem_serial);
	ArrayList<Question> getQuestionAll();
	ArrayList<Question> getQuestionsBySubCode(String sub_code);
	Question getQuestionBySerial(String question_serial);
	void updateQuestionCount(String question_serial, int question_count);
	void updateQuestion(Question question);
	void visibleQuestion(String question_serial);
	void img_file_processing(Question question, HttpServletRequest request);
	ArrayList<Question> getMyQuestionsBySubCode(String sub_code, int mem_serial);
}
