package com.spring.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.domain.Subject;
import com.spring.repository.Subject_Repository;

@Service
public class Subject_ServiceImpl implements Subject_Service {
	
	//Subject_Repository주입
	@Autowired
	private Subject_Repository subjectRepository;
	
	@Override
	public void addSubName(String name) {
		System.out.println("서비스 | Repository의 addNameChap()호출");
		subjectRepository.addSubName(name);
	}

	@Override
	public HashMap<String, Object> subNameCheck(HashMap<String, Object> map) {
		return subjectRepository.subNameCheck(map);
	}

	@Override
	public void addSubChap(Subject subject) {
		System.out.println("서비스 | Repository의 addSubChap()호출");
		subjectRepository.addSubChap(subject);
	}
	
	@Override
	public HashMap<String, Object> subChapCheck(HashMap<String, Object> map) {
		return subjectRepository.subChapCheck(map);
	}

	@Override
	public ArrayList<Subject> getAllSub() {
		System.out.println("서비스 | Repository의 getAllSub()호출");
		return subjectRepository.getAllSub();
	}

	@Override
	public ArrayList<Subject> getSubByName(String sub_name) {
		System.out.println("서비스 | Repository의 getSubByName() 호출");
		return subjectRepository.getSubByName(sub_name);
	}

	@Override
	public Subject getSubByChap(Subject subject) {
		System.out.println("서비스 | Repository의 getSubByChap() 호출");
		return subjectRepository.getSubByChap(subject);
	}


}
