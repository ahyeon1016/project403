package com.spring.service;

import java.util.List;

import com.spring.domain.Test;

public interface TestService 
{
	List<Test> getAllTestList();
	
	void setNewTest(Test test);
	
	void setDeleteTest(Integer test_num);
}
