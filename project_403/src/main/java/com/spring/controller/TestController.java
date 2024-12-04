package com.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.domain.Test;
import com.spring.service.TestService;

@Controller
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private TestService testService;
	
	// 시험지 전체보기
	@GetMapping("/testAll")
	public String testAll(Model model) {
		
		List<Test> arr = testService.getAllTestList();
		
		model.addAttribute("List", arr);				
				
		return "testAll";
	}
	
	// 시험지 추가하기 양식 제공
	@GetMapping("/testAdd")
	public String testAddForm(@ModelAttribute("NewTest") Test test)	{
		
		return "testAdd";
	}
	
	// 시험지 추가하기 DB 연결
	@PostMapping("/testAdd")
	public String testAddNew(@ModelAttribute("NewTest") Test test) {
		
		testService.setNewTest(test);
		
		return "redirect:/test/testAll";
	}
	
	// 시험지 수정하기 양식 제공
	@GetMapping("/testUpdate")
	public String testUpdateForm(@ModelAttribute("UpdateTest") Test test, @RequestParam("Num") Integer test_num, Model model) {
		
		Test testByNum = testService.getTestByNum(test_num);
		
		model.addAttribute("test", testByNum);
		
		return "testUpdate";
	}
	
	// 시험지 수정하기 DB 연결
	@PostMapping("/testUpdate")
	public String testUpdateSubmit(@ModelAttribute("UpdateTest") Test test) {
		
		testService.setUpdateTest(test);
		
		return "redirect:/test/testAll";
	}
	
	// 시험지 삭제하기
	@GetMapping("/testDelete")
	public String testDelete(@RequestParam("Num") Integer test_num) {
		
		testService.setDeleteTest(test_num);
		
		return "redirect:/test/testAll";
	}
}
