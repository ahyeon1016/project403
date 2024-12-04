package com.spring.repository;

import java.util.ArrayList;

import com.spring.domain.Subject;

public interface Subject_Repository {
	void addSub(Subject subject);
	ArrayList<Subject> getAllSub();
	ArrayList<Subject> getSubByName(String sub_name);
	Subject getSubByChap(Subject subject);
}
