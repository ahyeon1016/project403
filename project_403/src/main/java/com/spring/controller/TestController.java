package com.spring.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
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

import com.spring.domain.Member;
import com.spring.domain.Question;
import com.spring.domain.Subject;
import com.spring.domain.Test;
import com.spring.service.FnoteService;
import com.spring.service.Subject_Service;
import com.spring.service.TestService;

@Controller
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private Subject_Service subjectService;
	
	@Autowired
	private FnoteService fnoteservice;
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
		List<Subject> subList = testService.getSubList();
		List<Question> allQuestion = testService.getQuestion(testByNum);
		
		model.addAttribute("test", testByNum);
		model.addAttribute("subList", subList);
		model.addAttribute("allQuestion", allQuestion);
		
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
	
	// 시험 시작하기
	@GetMapping("/testStart")
	public String testStart(@RequestParam("Num") Integer test_num, Model model) {
		
		Test test = testService.getOneTestList(test_num);
		List<Question> allQuestion = testService.getQuestion(test);
		
		model.addAttribute("test", test);
		model.addAttribute("allQuestion", allQuestion);
		
		return "testStart";
	}
	//시험시작하기 버튼을 눌렀을때 기능
	   @RequestMapping(value="/callQuestion", method=RequestMethod.POST)
	   @ResponseBody
	   public Map<String, Object> callQuestion(@RequestParam("test_num") Integer test_num,HttpServletRequest req) {
	        HttpSession session=req.getSession(false);
	        Member member=(Member)session.getAttribute("member");
	        String mem_id=member.getMem_id();
	      Map<String, Object> rusultMap = new HashMap<>();
	      
	      Test test = testService.getOneTestList(test_num);
	      List<Question> allQuestion = testService.getQuestion(test);
//	      List<String[]> answerValue = testService.ansSelectValue(subCodeSum);
	      fnoteservice.note_create(mem_id, test_num);
	      rusultMap.put("allQuestion", allQuestion);
	      
	      return rusultMap;
	   }
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
//	@SuppressWarnings("unchecked")
	public Map<String, Object> qnaSelectRead(@RequestParam Map<String, Object> params) {
        
		Map<String, Object> resultMap = new HashMap<>();
		List<Object> qnaList = new ArrayList<Object>();
		
		String serials = null;
		
		if(params.get("test") != null) {
			String testNum = (String) params.get("test");
			
			int test_num = Integer.parseInt(testNum);
			Test testByNum = testService.getTestByNum(test_num);
			
			String[] serial = testByNum.getSerial();
			for(int i = 0; i < serial.length; i++) {
				if(i == 0) {
					serials = serial[i];
				} else {
					serials = serials + "," + serial[i];
				}
			}			
		}
		 
		// ajax에서 List 형태로 넘어올때 받는 코드
		String json = params.get("paramList").toString();
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, Object>> paramList = null;

	    try {
	    	String selectedSubject = (String)params.get("selectedSubject");
	    	
			paramList = mapper.readValue(json, new TypeReference<ArrayList<Map<String, Object>>>(){});
			for(int i = 0; i < paramList.size(); i++) {

				String sub_chap = (String)paramList.get(i).get("name");
				String subCodeSum = sub_code_sum(selectedSubject, sub_chap);
				
//				Map<String, Object> questionValue = (Map<String, Object>) testService.qnaSelectValue(subCodeSum);
//				Map<String, Object> answerValue = (Map<String, Object>) testService.ansSelectValue(subCodeSum);
			
				if(serials != null) {
					List<Question> questionValue = testService.qnaSelectValue(subCodeSum, serials);
					qnaList.add(questionValue);
				} else {
					List<Question> questionValue = testService.qnaSelectValue(subCodeSum);
					qnaList.add(questionValue);
				}
				List<String[]> answerValue = testService.ansSelectValue(subCodeSum);
				
				qnaList.add(answerValue);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		rusultMap.put("chapList", subjectService.getSubByName(sub_name));
//		rusultMap.put("chapList", testService.subValue(sub_name));
				
	    resultMap.put("qnaList", qnaList);	
	    
		return resultMap;
	}
	
	    
    @PostMapping("/search")
    @ResponseBody
    public Map<String, Object> search(@RequestParam String searchType, @RequestParam String searchText) {
        Map<String, Object> resultMap = new HashMap<>();
        
        try {
            // 검색 타입에 따른 쿼리 실행
            List<Test> searchResults = testService.search(searchType, searchText);
            
            resultMap.put("success", true);
            resultMap.put("data", searchResults);
            
        } catch (Exception e) {
        	resultMap.put("success", false);
        	resultMap.put("message", "검색 중 오류가 발생했습니다.");
        }
        
        return resultMap;
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
