package com.spring.controller;

import java.util.ArrayList;
import java.util.HashMap;

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

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/sub")
public class Subject_Controller {
	
	@Autowired
	private Subject_Service subjectService;
	
	//기본 매핑
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
	
	//sub name의 중복 확인을 위한 비동기 처리
	@ResponseBody
	@PostMapping("/subNameCheck")
	public HashMap<String, Object> subNameCheck(@RequestBody HashMap<String, Object> map) {
		System.out.println("==========================================");
		//DB에 sub_name이 존재하는지 확인하기 위해 이동
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
		System.out.println("컨트롤러 | addSubChap() 로 이동");
		//폼 페이지에서 작성된 subject를 가지고 서비스로 이동
		subjectService.addSubChap(subject);
		
		return "redirect:/sub";
	}
	
	//sub_chap의 중복 확인을 위한 비동기 처리
	@ResponseBody
	@PostMapping("/subChapCheck")
	public HashMap<String, Object> subChapCheck(@RequestBody HashMap<String, Object> map) {
		System.out.println("==========================================");
		//DB에 sub_name에 해당하는 sub_chap이 존재하는지 확인하기 위해 이동
		HashMap<String, Object> sum = subjectService.subChapCheck(map);
		System.out.println(sum.get("check"));
		return sum;
	}
	
	//Subject 테이블의 목록을 ArrayList로 가져오는 함수
	@RequestMapping("/sub_all")
	public String getSubAll(Model model) {
		System.out.println("==========================================");
		System.out.println("컨트롤러 | getSubAll 함수 호출");
		//DB에서 Subject 테이블의 모든 데이터를 ArrayList에 담아 가져온 후에 Model에 넣는다.
		ArrayList<Subject> sub_all = subjectService.getSubAll();
		model.addAttribute("sub_all", sub_all);
		return "Subject_all";
	}
	
	//Subject 테이블에서 특정 sub_name을 가진 행을 ArrayList로 가져오는 함수
	@GetMapping("/getSubByName")
	public String getSubByName(@RequestParam String sub_name, Model model) {
		System.out.println("==========================================");
		System.out.println("컨트롤러 | sub_name_search() 호출");
		//DB의 Subject 테이블에서 sub_name과 일치하는 모든 데이터를 ArrayList에 담아 가져온 후에 Model에 넣는다.
		ArrayList<Subject> sub_name_arr = subjectService.getSubByName(sub_name);
		model.addAttribute("sub_name_arr", sub_name_arr);
		return "Subject_name_search_view";
	}
	
	//Subject 테이블에서 특정 sub_name과 sub_chap을 가진 행을 DTO로 가져오는 함수
	@GetMapping("/getSubByChap")
	public String getSubByChap(@ModelAttribute Subject subject, Model model) {
		System.out.println("==========================================");
		System.out.println("컨트롤러 | getSubByChap() 호출");
		//DB에서 가져온 Subject DTO와 폼에서 받은 Subject DTO를 Model에 넣는다.
		Subject subByChap = subjectService.getSubByChap(subject);
		model.addAttribute("subByChap", subByChap);
		model.addAttribute("inputSub", subject);
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
		//DB에서 기존 sub_name을 찾기 위한 데이터와 수정된 sub_name을 파라미터로 가지고 이동 
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
