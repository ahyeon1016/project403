package com.spring.repository;

import java.util.List;

import com.spring.domain.Test;

public interface TestRepository 
{
	List<Test> getAllTestList();
	
	void setNewTest(Test test);
	
	void setDeleteTest(Integer test_num);
}
