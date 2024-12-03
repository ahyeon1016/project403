package com.spring.service;

import java.util.ArrayList;

import com.spring.domain.Subject;

public interface Subject_Service {
	void addSub(Subject subject);
	ArrayList<Subject> getAllSub();
	ArrayList<Subject> getSubByName(String sub_name);
}
