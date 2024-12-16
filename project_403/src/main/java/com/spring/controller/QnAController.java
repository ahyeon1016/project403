package com.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.service.QnA_Service;

@Controller
@RequestMapping("QnA")
public class QnAController {
	
	//@Autowired
	//QnA_Service qnaService;
	
	@RequestMapping("/main")
	public String main() {
		return "QnA_main";
	}
	
	//comment작성 페이지로 이동
	@GetMapping("/comment_add")
	public String comment_add() {
		
		return "QnA_add_comment";
	}
}
