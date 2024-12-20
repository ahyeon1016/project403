package com.spring.repository;

import java.util.List;

import com.spring.domain.Question;
import com.spring.domain.Subject;
import com.spring.domain.Test;

public interface TestRepository 
{
//	페이징 없이 All
//	List<Test> getAllTestList();
	
	// 페이징 처리 Read All
	List<Test> getBoardList(Integer pageNum, int limit);
	
	// Test 테이블 전체 글 숫자 계산
	int getListCount();
	
	void setNewTest(Test test);
	
	void setDeleteTest(Integer test_num);
	
	Test getTestByNum(Integer test_num);
	
	void setUpdateTest(Test test);

	Test getOneTestList(Integer test_num);

	Test getTestValue(Integer test_num);

	List<Subject> getSubList();

	List<Subject> subValue(String sub_name);

	List<Question> qnaSelectValue(String subCodeSum);

	List<String[]> ansSelectValue(String subCodeSum);

	List<Question> getQuestion(Test test);
}
