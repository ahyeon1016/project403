package com.spring.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.spring.domain.Subject;

public interface Subject_Service {
	void addSub(Subject subject);
	ArrayList<Subject> getAllSub();
	ArrayList<Subject> getSubByName(String sub_name);
	Subject getSubByChap(Subject subject);
	HashMap<String, Object> subChapCheck(HashMap<String, Object> map); 
}
