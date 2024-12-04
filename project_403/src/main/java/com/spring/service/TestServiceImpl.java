package com.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.domain.Test;
import com.spring.repository.TestRepository;

@Repository
public class TestServiceImpl implements TestService {
	
	@Autowired
	private TestRepository testRepository;

	@Override
	public List<Test> getAllTestList() {
		
		return testRepository.getAllTestList();
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
	
	
	
}
