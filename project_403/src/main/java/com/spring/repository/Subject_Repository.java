package com.spring.repository;

import java.util.ArrayList;
import java.util.HashMap;

import com.spring.domain.Subject;

public interface Subject_Repository {
	void addSubName(String name);
	void addSubChap(Subject subject);
	ArrayList<Subject> getAllSub();
	ArrayList<Subject> getSubByName(String sub_name);
	Subject getSubByChap(Subject subject);
	HashMap<String, Object> subChapCheck(HashMap<String, Object> map); 
}
