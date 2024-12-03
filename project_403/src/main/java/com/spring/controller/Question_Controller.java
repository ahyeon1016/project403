package com.spring.controller;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.spring.domain.Question;
import com.spring.service.Question_Service;

@Controller
@RequestMapping("/Q")
public class Question_Controller {
	
	@Autowired
	private Question_Service questionService;
	
	@GetMapping("/add")
	public String Question_Insert_Form(@ModelAttribute Question question) {
		System.out.println("컨트롤러 | Question_add_form로 이동");
		return "Question_add_form";
	}
	
	@PostMapping("/add")
	public String Question_Insert(@ModelAttribute Question question, HttpServletRequest request) {
		System.out.println("컨트롤러 | Question_Insert_DB() 호출");
		
		//파일 이름 만들기
		String path = request.getServletContext().getRealPath("/resources/images");
		String name = question.getQuestion_img().getOriginalFilename();
		
		long time = System.currentTimeMillis();
		
		String savename = name+time;
		System.out.println(path);
		System.out.println(savename);
		//빈 파일 생성
		File file = new File(path ,savename);
		
		//빈 파일에 내용 작성
		
		
		questionService.Question_Insert_DB(question);
		return "Question_view";
	}
	
}
