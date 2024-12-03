package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.domain.Subject;

@Controller
@RequestMapping("/sub")
public class Subject_Controller {
	
	@RequestMapping
	public String Sub_main() {
		return "Subject_main";
	}
	
	//Subject 작성 폼 페이지로 이동
	@GetMapping("sub_form")
	public String Sub_Form(@ModelAttribute Subject subject) {
		System.out.println("컨트롤러 | Subject 폼 페이지로 이동");
		return "Subject_form";
	}
}
