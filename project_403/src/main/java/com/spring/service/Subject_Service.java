package com.spring.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.spring.domain.Subject;

public interface Subject_Service {
	void addSubName(String name);
	HashMap<String, Object> subNameCheck(HashMap<String, Object> map); 
	void addSubChap(Subject subject);
	HashMap<String, Object> subChapCheck(HashMap<String, Object> map); 
	ArrayList<Subject> getSubChapAll();
	ArrayList<Subject> getSubByName(String sub_name);
	Subject getSubByChap(Subject subject);
}
