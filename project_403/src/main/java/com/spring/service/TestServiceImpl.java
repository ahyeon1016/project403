package com.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.domain.Question;
import com.spring.domain.Subject;
import com.spring.domain.Test;
import com.spring.repository.TestRepository;

@Repository
public class TestServiceImpl implements TestService {
	
	@Autowired
	private TestRepository testRepository;

	// 페이징 처리 Read All
	@Override
	public List<Test> getBoardList(Integer pageNum, int limit) {
		
		List<Test> list = testRepository.getBoardList(pageNum, limit);
		
		return list;
	}
	
	// Test 테이블 전체 글 숫자 계산
	@Override
	public int getListCount() {
		
		int count = testRepository.getListCount();
		
		return count;
	}
	
	@Override
	public void setNewTest(Test test) {
		
		testRepository.setNewTest(test);
	}

	@Override
	public void setDeleteTest(Integer test_num) {
		
		testRepository.setDeleteTest(test_num);
	}

	@Override
	public Test getTestByNum(Integer test_num) {
		
		Test testByNum = testRepository.getTestByNum(test_num);
		
		return testByNum;
	}

	@Override
	public void setUpdateTest(Test test) {
		
		testRepository.setUpdateTest(test);
	}

	@Override
	public Test getOneTestList(Integer test_num) {
		
		Test test = testRepository.getOneTestList(test_num);
		
		return test;
	}

	@Override
	public Test getTestValue(Integer test_num) {
		
		Test test = testRepository.getTestValue(test_num);
		
		return test;
	}

	@Override
	public List<Subject> getSubList() {
		
		List<Subject> list = testRepository.getSubList();
		
		return list;
	}

	@Override
	public List<Subject> subValue(String sub_name) {
		
		List<Subject> list = testRepository.subValue(sub_name);
		
		return list;
	}

	@Override
	public List<Question> qnaSelectValue(String subCodeSum, String serial) {
		
		List<Question> list = testRepository.qnaSelectValue(subCodeSum, serial);
		
		return list;
	}

	/*
	 * @Override public List<Question> qnaSelectValue(String subCodeSum) {
	 * 
	 * List<Question> list = testRepository.qnaSelectValue(subCodeSum);
	 * 
	 * return list; }
	 */

	@Override
	public List<String[]> ansSelectValue(String subCodeSum) {
		
		List<String[]> list = testRepository.ansSelectValue(subCodeSum);
		
		return list;
	}

	@Override
	public List<Question> getQuestion(Test test) {
		
		List<Question> question = testRepository.getQuestion(test);
		
		return question;
	}

	@Override
	public List<Test> search(String searchType, String searchText, String sessionId, Integer pageNumber, int limit) {
		
		List<Test> list = testRepository.search(searchType, searchText, sessionId, pageNumber, limit);
		
		return list;
	}

	@Override
	public int searchListCount(String searchType, String searchText) {
		
		int count = testRepository.searchListCount(searchType, searchText);
		
		return count;
	}
	
	
	
}
