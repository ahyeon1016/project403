package com.spring.controller;

import java.util.ArrayList;
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

import page.Repository;

@Controller
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private TestService testService;
	
	// 시험지 전체보기
	@GetMapping("/testAll")
	public String testAll(@RequestParam(value = "pageNum", required = false) Integer pageNum, Model model) {	
		//페이징 테스트코드		
		int limit = 5;
		
		if(pageNum == null)
		{
			pageNum = 1;
		}
		
		Repository rrr = new Repository();
		int total_record = rrr.getListCount();
		ArrayList<Test> boardlist = new ArrayList<Test>();
		boardlist = rrr.getBoardList(pageNum, limit);
		
		int total_page;
		
		if (total_record % limit == 0){     
	     	total_page = total_record/limit;
	     	Math.floor(total_page);  
		}
		else{
		   total_page = total_record/limit;
		   Math.floor(total_page); 
		   total_page =  total_page + 1; 
		}
		
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("total_page", total_page);
		model.addAttribute("total_record",total_record);
		model.addAttribute("boardlist", boardlist);
		//페이징 테스트코드
		
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
