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
	public ArrayList<Subject> getSubAll() {
		System.out.println("서비스 | Repository의 getSubAll()호출");
		return subjectRepository.getSubAll();
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

	@Override
	public void updateSubName(String old_sub_name, String new_sub_name) {
		System.out.println("서비스 | Repository의 updateSubName() 호출");
		subjectRepository.updateSubName(old_sub_name, new_sub_name);
	}

	@Override
	public void updateSubChap(String sub_name, String old_sub_chap, String new_sub_chap) {
		System.out.println("서비스 | Repository의 updateSubChap() 호출");
		subjectRepository.updateSubChap(sub_name, old_sub_chap, new_sub_chap);
	}

	@Override
	public void deleteSubName(String sub_name) {
		System.out.println("서비스 | Repository의 deleteSubName() 호출");
		subjectRepository.deleteSubName(sub_name);
	}

	@Override
	public void deleteSubChap(String sub_name, String sub_chap) {
		System.out.println("서비스 | Repository의 deleteSubChap() 호출");
		subjectRepository.deleteSubChap(sub_name, sub_chap);
	}

	@Override
	public ArrayList<Subject> getSubAllName() {
		System.out.println("서비스 | Repository의 getSubAllName() 호출");
		return subjectRepository.getSubAllName();
	}

	@Override
	public HashMap<String, Object> subNameByValue(HashMap<String, Object> map) {
		return subjectRepository.subNameByValue(map);
	}
	
	//선택된 과목의 고유 넘버를 만드는 함수로 다른 컨트롤러에서 사용하기 때문에 서비스계층으로 올렸음.
	@Override
	public String sub_code_sum(String sub_name, String sub_chap) {
 		System.out.println("서비스 | sub_code_sum() 도착");
		Subject sub = new Subject();
		sub.setSub_name(sub_name); 
		sub.setSub_chap(sub_chap);
		Subject return_sub = subjectRepository.getSubByChap(sub);
		//가져온 DTO에서 코드를 가져와 문자열로 캐스팅하여 과목의 고유 넘버를 만든다.
		String sub_name_code = String.valueOf(return_sub.getSub_name_code()); 
		String sub_chap_code = String.valueOf(return_sub.getSub_chap_code());
		String sub_code_sum = sub_name_code+"_"+sub_chap_code;
		System.out.println("서비스 | sub_code_sum 값 : "+sub_code_sum);
		return sub_code_sum;
	}
	
}
