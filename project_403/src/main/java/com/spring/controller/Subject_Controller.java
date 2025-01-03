package com.spring.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.domain.Subject;
import com.spring.service.Subject_Service;

@Controller
@RequestMapping("/sub")
public class Subject_Controller {
	
	/*
	 * 일단 개발 과정에서 요청 파라미터를 확인 하고 싶어 GET방식으로 요청을 받았으나,
	 * 이후에 POST방식으로 변경하는 것을 고민해볼 것 
	 * */
	
	//Subject_Service주입
	@Autowired
	private Subject_Service subjectService;
	
	//기본 매핑
	//main 페이지에서 폼을 작성하는 경우도 있기 때문에 DTO를 들고감
	@RequestMapping
	public String Sub_main(@ModelAttribute Subject subject) { 
		return "Subject_main";
	}
	
	//Subject name 작성 폼 페이지로 이동
	@GetMapping("/sub_name_form")
	public String Sub_name_form() {
		System.out.println("==========================================");
		System.out.println("컨트롤러 | Subject name 폼 페이지로 이동");		
		return "Subject_name_form";
	}
	
	//Subject_name_form에서 작성한 내용을 Post 방식으로 받아 처리
	@PostMapping("/sub_name_form")
	public String Sub_add_name(HttpServletRequest request) {
		System.out.println("==========================================");
		System.out.println("컨트롤러 | sub_name을 가지고 서비스로 이동");
		String name = request.getParameter("sub_name");
		
		subjectService.addSubName(name);
		
		return "redirect:/sub";
	}
	
	//Subject name 중복 확인
	@ResponseBody
	@PostMapping("/subNameCheck")
	public HashMap<String, Object> subNameCheck(@RequestBody HashMap<String, Object> map) {
		System.out.println("==========================================");
		HashMap<String, Object> sum = subjectService.subNameCheck(map);
		System.out.println(sum.get("check"));
		return sum;
	}
	
	//Subject chap 작성 폼 페이지로 이동
	@GetMapping("/sub_chap_form")
	public String Sub_chap_form(@ModelAttribute Subject subject) {
		System.out.println("==========================================");
		System.out.println("컨트롤러 | Subject chap 폼 페이지로 이동");
		return "Subject_chap_form";
	}
	
	//Subject_chap_form에서 작성한 내용을 Post 방식으로 받아 처리
	@PostMapping("/sub_chap_form")
	public String Sub_add_chap(@ModelAttribute Subject subject, Model model) {
		System.out.println("==========================================");
		System.out.println("컨트롤러 | model을 가지고 서비스로 이동");
		
		subjectService.addSubChap(subject);
		
		return "redirect:/sub";
	}
	
	//Subject_chap 중복 확인
	@ResponseBody
	@PostMapping("/subChapCheck")
	public HashMap<String, Object> subChapCheck(@RequestBody HashMap<String, Object> map) {
		System.out.println("==========================================");
		HashMap<String, Object> sum = subjectService.subChapCheck(map);
		System.out.println(sum.get("check"));
		return sum;
	}
	
	//Subject 테이블의 목록을 ArrayList로 가져오는 함수
	@RequestMapping("/sub_all")
	public String getSubAll(Model model) {
		System.out.println("==========================================");
		System.out.println("컨트롤러 | Sub_chap_all 함수 호출");
		ArrayList<Subject> sub_all = subjectService.getSubAll();
		model.addAttribute("sub_all", sub_all);
		return "Subject_all";
	}
	
	//Subject 테이블에서 특정 sub_name을 가진 행을 ArrayList로 가져오는 함수
	@GetMapping("/getSubByName")
	public String getSubByName(@RequestParam String sub_name, Model model) {
		System.out.println("==========================================");
		System.out.println("컨트롤러 | sub_name_search() 호출");
		System.out.println(sub_name);
		ArrayList<Subject> sub_name_arr = subjectService.getSubByName(sub_name);
		model.addAttribute("sub_name_arr", sub_name_arr);
		return "Subject_name_search_view";
	}
	
	//Subject 테이블에서 특정 sub_name과 sub_chap을 가진 행을 DTO로 가져오는 함수
	@GetMapping("/getSubByChap")
	public String getSubByChap(@ModelAttribute Subject subject, Model model) {
		System.out.println("==========================================");
		System.out.println("컨트롤러 | getSubByChap() 호출");
		Subject subByChap = subjectService.getSubByChap(subject);
		model.addAttribute("subByChap", subByChap);
		model.addAttribute("inputSub",subject);
		return "Subject_chap_search_view";
	}
	
	//sub_name 수정 폼 페이지로 이동
	@GetMapping("/updateSubName")
	public String updateSubNameForm(@RequestParam String sub_name, Model model) {
		System.out.println("==========================================");
		System.out.println("컨트롤러 | sub_name 수정을 위한 폼 페이지로 이동");
		model.addAttribute("sub_name", sub_name);
		return "Subject_update_name";
	}
	
	//기존 sub_name과 수정된 sub_name을 파라미터로 받아 처리하는 함수
	@PostMapping("/updateSubName")
	public String updateSubName(@RequestParam String old_sub_name, @RequestParam String new_sub_name) {
		System.out.println("==========================================");
		System.out.println("컨트롤러 | old_sub_name : "+old_sub_name+" / new_sub_name : "+new_sub_name);
		subjectService.updateSubName(old_sub_name, new_sub_name);
		return "redirect:/sub/sub_all";
	}

	//sub_chap 수정 폼 페이지로 이동
	@GetMapping("/updateSubChap")
	public String updateSubChapForm(
			@RequestParam String sub_name,
			@RequestParam String sub_chap, 
			Model model) {
		System.out.println("==========================================");
		System.out.println("컨트롤러 | sub_chap 수정을 위한 폼 페이지로 이동");
		model.addAttribute("sub_name", sub_name);
		model.addAttribute("sub_chap", sub_chap);
		return "Subjcet_update_chap";
	}
	
	//sub_name, 기존 sub_chap, 수정된 sub_chap을 파라미터로 받아 처리하는 함수
	@PostMapping("/updateSubChap")
	public String updateSubChap(
			@RequestParam String sub_name,
			@RequestParam String old_sub_chap,
			@RequestParam String new_sub_chap) {
		System.out.println("==========================================");
		subjectService.updateSubChap(sub_name, old_sub_chap, new_sub_chap);
		return "redirect:/sub/sub_all";
	}

	//sub_name(과목)을 제거하는 함수
	@GetMapping("/deleteSubName")
	public String deleteSubName(@RequestParam String sub_name) {
		System.out.println("==========================================");
		System.out.println("컨트롤러 | deleteSubName() 도착");
		subjectService.deleteSubName(sub_name);
		return "redirect:/sub/sub_all";
	}

	//sub_name과 일치하는 sub_chap(챕터)을 제거하는 함수
	@GetMapping("/deleteSubChap")
	public String deleteSubChap(
			@RequestParam String sub_name, 
			@RequestParam String sub_chap) {
		System.out.println("==========================================");
		System.out.println("컨트롤러 | deleteSubChap() 도착");
		subjectService.deleteSubChap(sub_name, sub_chap);
		return "redirect:/sub/sub_all";
	}

}
