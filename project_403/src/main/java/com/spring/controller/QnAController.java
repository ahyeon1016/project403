package com.spring.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.domain.QnA;
import com.spring.service.QnA_Service;

@Controller
@RequestMapping("/QnA")
public class QnAController {
	
	@Autowired
	QnA_Service qnaService;
	
	@RequestMapping("/main")
	public String main() {
		return "QnA_main";
	}
	
	//comment_root작성 페이지로 이동
	@GetMapping("/addCommentRoot")
	public String addCommentRoot_form(@RequestParam String question_serial, @ModelAttribute("qna") QnA qna) {
		System.out.println("==========================================");
		System.out.println("컨트롤러 | addCommentRoot_form()도착");
	    qna.setQuestion_serial(question_serial);
		return "QnA_addCommentRoot_form";
	}
	
	//폼 페이지에서 작성된 comment_root 정보를 가지고 DB로 이동 
	@PostMapping("/addCommentRoot")
	public String addCommentRoot(@ModelAttribute("qna") QnA qna) {
		System.out.println("==========================================");
		System.out.println("컨트롤러 | addCommentRoot() 도착");
		
		//qna를 가지고 DB로 이동
		qnaService.addCommentRoot(qna);
		
		return "redirect:/QnA/main";
	}
	
}
