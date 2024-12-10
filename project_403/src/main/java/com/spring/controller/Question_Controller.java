package com.spring.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

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
import com.spring.domain.Subject;
import com.spring.service.Question_Service;
import com.spring.service.Subject_Service;

@Controller
@RequestMapping("/Q")
public class Question_Controller {
	
	@Autowired
	private Question_Service questionService;
	
	@Autowired
	private Subject_Service subjectService;
	
	//question 메인 페이지 임시로 지정함. 추후 수정 예정
	@RequestMapping("/main")
	public String temporary() {
		return "Question_main";
	}
	
	//객관식 문제 작성 폼 페이지로 이동
	@GetMapping("/Q_addMCQ")
	public String Q_addMCQ_form(@ModelAttribute Question question, Model model) {
		ArrayList<Subject> sub_all = subjectService.getSubAll();
		ArrayList<Subject> sub_all_name = subjectService.getSubAllName();
		System.out.println(sub_all_name.get(0).getSub_name());
		model.addAttribute("sub_all", sub_all);
		model.addAttribute("sub_all_name", sub_all_name);
		System.out.println("컨트롤러 | Question_addMCQ_form로 이동");
		return "Question_addMCQ_form";
	}
	
	//Question_addMCQ_form 페이지에서 입력받은 정보를 받아 처리 후에 DAO에 전달
	@PostMapping("/Q_addMCQ")
	public String Q_addMCQ(@ModelAttribute Question question, HttpServletRequest request) {
		System.out.println("컨트롤러 | Q_addMCQ() 도착");
		System.out.println(question.getQuestion_img().getSize());
		//전처리
		//폼 페이지에서 select한 과목과 챕터를 가져와 DB에서 일치하는 DTO를 가져오는 작업
		String sub_name = request.getParameter("name_select");
		String sub_chap = request.getParameter("chap_select");
		
		Subject sub = new Subject();
		sub.setSub_name(sub_name); 
		sub.setSub_chap(sub_chap);
		Subject return_sub = subjectService.getSubByChap(sub);
		//가져온 DTO에서 코드를 가져와 문자열로 캐스팅하여 과목의 고유 넘버를 만든다.
		String sub_name_code = String.valueOf(return_sub.getSub_name_code()); 
		String sub_chap_code = String.valueOf(return_sub.getSub_chap_code());
		String sub_code_sum = sub_name_code+"_"+sub_chap_code;
		//DTO에 SET
		question.setSub_code_sum(sub_code_sum);
		
		//정답의 쉼표를 다른 문자로 변환
		question.setQuestion_ans(question.getQuestion_ans().replace(",", "|★|"));
		System.out.println("컨트롤러 | Q_addMCQ() 정답 : "+question.getQuestion_ans());
		
		//ModelAttribute로 데이터를 받은 DTO에서 이미지 파일을 처리
		//파일 저장 경로
		String path = request.getServletContext().getRealPath("/resources/images");
		//파일 이름 만들기
		//파일의 확장자를 분리하는 작업
		if(question.getQuestion_img().getSize()!=0) {
			MultipartFile multi = question.getQuestion_img();
			String file_name = multi.getOriginalFilename();
			String[] file_names = file_name.split("\\.");
			String extension = file_names[file_names.length-1];
			//파일의 이름을 현재시간으로 사용하기 위한 작업
			long time = System.currentTimeMillis();
			SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmssSSS");
			//결합 : RLPL209912311030123.확장자
			String img_name = "RLPL"+sdf.format(time)+"."+extension;
			System.out.println("저장되는 파일 : "+img_name);
			//DTO에 SET
			question.setQuestion_img_name(img_name);
			
			//빈 파일 생성
			File file = new File(path, img_name); 
		
			//빈 파일에 작성
			try {
				multi.transferTo(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("컨트롤러 | Q_addMCQ() 이미지 파일이 없음");
		}
		//서비스 이동
		System.out.println("컨트롤러 | addMCQ() 호출");
		questionService.addMCQ(question);
		
		return "Question_view";
	}
}
