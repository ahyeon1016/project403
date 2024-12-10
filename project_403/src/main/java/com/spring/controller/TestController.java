package com.spring.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.domain.Test;
import com.spring.service.TestService;

@Controller
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private TestService testService;
	
	// 시험지 전체보기
	@GetMapping("/testAll")
	public String testAll(@RequestParam(value = "pageNum", required = false) Integer pageNum, Model model) {	
		
		// 페이징 처리 Read All
		int limit = 5;
		
		if(pageNum == null)
		{
			pageNum = 1;
		}
				
		int total_record = testService.getListCount();
		List<Test> boardlist = new ArrayList<Test>();
		boardlist = testService.getBoardList(pageNum, limit);
		
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
		// 페이징 처리 Read All
		
		// 페이징 없이 Read All
//		List<Test> arr = testService.getAllTestList();		
//		model.addAttribute("List", arr);
		// 페이징 없이 Read All
		
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
	
	// 시험지 상세보기
	@GetMapping("/testOneView")
	public String testOneView(@RequestParam("Num") Integer test_num, Model model) {
		
		Test test = testService.getOneTestList(test_num);
		
		model.addAttribute("test", test);
		
		return "testOneView";
	}
	
	// 시험지 상세보기 비밀번호 입력 ajax
	@RequestMapping(value="/testValue", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> testValue(@RequestParam("password") String password, @RequestParam("test_num") Integer test_num) {
        
		Map<String, Object> response = new HashMap<>();
		
		Test test = testService.getTestValue(test_num);
		
		if (test != null && test.getTest_pw().equals(password)) {
	        response.put("success", true);
	        response.put("test_num", test_num);
	    } else {
	        response.put("success", false);
	    }
	
		return response;
	}
}
