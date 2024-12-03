package com.spring.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.domain.Subject;
import com.spring.service.Subject_Service;

@Controller
@RequestMapping("/sub")
public class Subject_Controller {
	
	//Subject_Service주입
	@Autowired
	private Subject_Service subjectService;
	
	//기본 매핑
	@RequestMapping
	public String Sub_main() {
		return "Subject_main";
	}
	
	//Subject 작성 폼 페이지로 이동
	@GetMapping("/sub_form")
	public String Sub_form(@ModelAttribute Subject subject) {
		System.out.println("컨트롤러 | Subject 폼 페이지로 이동");
		return "Subject_form";
	}
	
	//Subject_form에서 작성한 내용을 Post 방식으로 받아 처리
	@PostMapping("/sub_form")
	public String Sub_add(@ModelAttribute Subject subject, Model model) {
		System.out.println(subject.getSub_name());
		
		subjectService.addSub(subject);
		
		model.addAttribute("name", subject.getSub_name());
		model.addAttribute("chap", subject.getSub_chap());
		return "Subject_view";
	}
	
	//Subject 테이블의 목록을 ArrayList로 가져오는 함수
	@RequestMapping("/sub_all")
	public String Sub_all(Model model) {
		System.out.println("컨트롤러 | Sub_all 함수 호출");
		ArrayList<Subject> sub_all = subjectService.getAllSub();
		model.addAttribute("sub_all", sub_all);
		return "Subject_all";
	}
	
}
