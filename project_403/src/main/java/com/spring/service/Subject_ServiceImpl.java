package com.spring.service;

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
	public void addSub(Subject subject) {
		System.out.println("서비스 | Repository의 addSub()호출");
		subjectRepository.addSub(subject);;
	}

}
