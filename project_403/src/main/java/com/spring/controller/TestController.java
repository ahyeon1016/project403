package com.spring.controller;

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

import com.spring.domain.Question;
import com.spring.domain.Subject;
import com.spring.domain.Test;
import com.spring.service.Subject_Service;
import com.spring.service.TestService;

@Controller
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private Subject_Service subjectService;
	
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
		List<Test> boardList = testService.getBoardList(pageNum, limit);
		
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
		model.addAttribute("boardList", boardList);
		
		return "testAll";
	}
	
	// 시험지 추가하기 양식 제공
	@GetMapping("/testAdd")
	public String testAddForm(@ModelAttribute("NewTest") Test test, Model model)	{
		
		List<Subject> subList = testService.getSubList();
		
		model.addAttribute("subList", subList);
		
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
		List<Question> allQuestion = testService.getQuestion(test);
		
		model.addAttribute("test", test);
		model.addAttribute("allQuestion", allQuestion);
		
		return "testOneView";
	}
	
	// 시험지 상세보기 비밀번호 입력 ajax
	@RequestMapping(value="/testValue", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> testValue(@RequestParam("password") String password, @RequestParam("test_num") Integer test_num) {
        
		Map<String, Object> rusultMap = new HashMap<>();
		
		Test test = testService.getTestValue(test_num);
		
		if (test != null && test.getTest_pw().equals(password)) {
			rusultMap.put("success", true);
	        rusultMap.put("test_num", test_num);
	    } else {
	    	rusultMap.put("success", false);
	    }
	
		return rusultMap;
	}
	
	// 시험지 과목+챕터 select 작동 ajax
	@RequestMapping(value="/subValue", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> subValue(@RequestParam Map<String, Object> params) {
        
		Map<String, Object> rusultMap = new HashMap<>();
		
		String sub_name = (String)params.get("sub_name");
		
		rusultMap.put("chapList", subjectService.getSubByName(sub_name));
//		rusultMap.put("chapList", testService.subValue(sub_name));
		
//		List<Subject> chapList = new ArrayList<Subject>();		
//		chapList = testService.subValue(sub_name);
//		rusultMap.put("chapList", chapList);
	    
		return rusultMap;
	}
	
	// 과목+챕터 선택시 해당 문제 출력 ajax
	@RequestMapping(value="/qnaSelectRead", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> qnaSelectRead(@RequestParam Map<String, Object> params) {
        
		Map<String, Object> rusultMap = new HashMap<>();
		
		String sub_name = (String)params.get("sub_name");
		String sub_chap = (String)params.get("sub_chap");
		
//		rusultMap.put("chapList", subjectService.getSubByName(sub_name));
//		rusultMap.put("chapList", testService.subValue(sub_name));		
				
		String subCodeSum = sub_code_sum(sub_name, sub_chap);
				
		rusultMap.put("qnaList", testService.qnaSelectValue(subCodeSum));
		rusultMap.put("ansList", testService.ansSelectValue(subCodeSum));
	    
		return rusultMap;
	}	
	
//	여기서부터 나중에 삭제해야됨->
	private String sub_code_sum(String sub_name, String sub_chap) {
	      System.out.println("컨트롤러 | sub_code_sum() 도착");
	      Subject sub = new Subject();
	      sub.setSub_name(sub_name); 
	      sub.setSub_chap(sub_chap);
	      Subject return_sub = subjectService.getSubByChap(sub);
	      //가져온 DTO에서 코드를 가져와 문자열로 캐스팅하여 과목의 고유 넘버를 만든다.
	      String sub_name_code = String.valueOf(return_sub.getSub_name_code()); 
	      String sub_chap_code = String.valueOf(return_sub.getSub_chap_code());
	      String sub_code_sum = sub_name_code+"_"+sub_chap_code;
	      //DTO에 SET
	      return sub_code_sum;
	   }
//	<-여기까지 나중에 삭제해야됨
	
	
}
