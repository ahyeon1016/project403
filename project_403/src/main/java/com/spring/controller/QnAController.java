package com.spring.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	//모든 comment_root정보를 가져오기 위해 DB로 이동하는 함수
	@GetMapping("/commentRootAll")
	public String getCommentRootAll(Model model) {
		System.out.println("==========================================");
		System.out.println("컨트롤러 | getCommentRootAll() 도착");
		
		//DB로 이동하여 모든 comment_root 정보를 가져옴
		ArrayList<QnA> rootAll = qnaService.getCommentRootAll();
		//모델에 저장		
		model.addAttribute("rootAll", rootAll);
	
		return "QnA_commentRootAll";
	}
	
	//comment_root와 일치하는 DTO를 가져오기 위해 DB로 이동하는 함수
	@GetMapping("/commentRootOne")
	public String getCommentRootOne(
			@RequestParam int comment_root,
			Model model) {
		System.out.println("==========================================");
		System.out.println("컨트롤러 | getCommentRootOne() 도착");
		
		QnA qna = qnaService.getCommentRootOne(comment_root);
		model.addAttribute("qna", qna);
		
		return "QnA_commentRoot";
	}
	
	//comment_parent를 추가하기 위해 ajax로 데이터를 받아 처리하는 함수
	@ResponseBody
	@PostMapping("/addCommentParent")
	public HashMap<String, Object> addCommentParent(
			@RequestBody HashMap<String, Object> map){
		System.out.println("==========================================");
		System.out.println("컨트롤러 | addCommentParent() 도착");
		System.out.println("comment_parent를 작성한 유저 : "+map.get("mem_id"));
		System.out.println("comment_parent를 작성한 root번호 : "+map.get("comment_root"));
		System.out.println("comment_parent의 내용 : "+map.get("comment_content"));
		
		map = qnaService.addCommentParent(map);
		
		return map;
	}
}
