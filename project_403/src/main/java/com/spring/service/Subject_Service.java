package com.spring.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.spring.domain.Subject;

public interface Subject_Service {
	void addSubName(String name);
	HashMap<String, Object> subNameCheck(HashMap<String, Object> map); 
	void addSubChap(Subject subject);
	HashMap<String, Object> subChapCheck(HashMap<String, Object> map); 
	ArrayList<Subject> getSubAll();
	ArrayList<Subject> getSubByName(String sub_name);
	Subject getSubByChap(Subject subject);
	void updateSubName(String old_sub_name, String new_sub_name);
	void updateSubChap(String sub_name, String old_sub_chap, String new_sub_chap);
	void deleteSubName(String sub_name);
	void deleteSubChap(String sub_name, String sub_chap);
	public ArrayList<Subject> getSubAllName();
	HashMap<String, Object> subNameByValue(HashMap<String, Object> map);
}
