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
import com.spring.service.MemberService;
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
	
	@Autowired
	MemberService memberservice;
	
	// 시험지 전체보기
	@GetMapping("/testAll")
	public String testAll(@RequestParam(value = "pageNum", required = false) Integer pageNum, Model model) {	
		
		// 페이징 처리
		int limit = 5;
		
		if(pageNum == null)
		{
			pageNum = 1;
		}
		
		// Test 테이블 전체 글 갯수 계산
		int total_record = testService.getListCount();
		// 페이징 처리 n 번째부터 limit 갯수만큼 가져오기
		List<Test> boardList = testService.getBoardList(pageNum, limit);
		// 총 페이지 숫자 구하기
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
		//model.addAttribute("nickName", nickName);
		
		return "testAll";
	}	
	
	// 시험지 추가하기 양식 제공
	@GetMapping("/testAdd")
	public String testAddForm(@ModelAttribute("NewTest") Test test, Model model, HttpSession session)	{
		
		// 로그인 상태가 아니라면 로그인 페이지로 redirect
		Member loginCheck = (Member) session.getAttribute("member"); 
	    if (loginCheck == null) {
	        return "redirect:/member/login";
	    }
		
	    // Subject 테이블 sub_name(과목) 칼럼값 가져오기(중복제외)
		List<Subject> subList = testService.getSubList();
		
		model.addAttribute("subList", subList);
		
		return "testAdd";
	}
	
	// 시험지 추가하기 DB 연결
	@PostMapping("/testAdd")
	public String testAddNew(@ModelAttribute("NewTest") Test test, HttpSession session) {
		
		// testAdd 페이지에서 넘어온 mem_id 값 변경(mem_nickName -> mem_id)
		Member member = (Member) session.getAttribute("member");
		test.setMem_id(member.getMem_id());
		// Create: 시험지 생성하기
		testService.setNewTest(test);
		
		return "redirect:/test/testAll";
	}
	
	// 시험지 수정하기 양식 제공
	@GetMapping("/testUpdate")
	public String testUpdateForm(@ModelAttribute("UpdateTest") Test test, @RequestParam("Num") Integer test_num, Model model) {
		
		// DB에서 test_num에 해당하는 행 가져오기
		Test testByNum = testService.getTestByNum(test_num);
		// Subject 테이블 sub_name(과목) 칼럼값 가져오기(중복제외)
		List<Subject> subList = testService.getSubList();
		// serial(과목챕터코드) 해당하는 문제를 Question 테이블에서 가져오기
		List<Question> allQuestion = testService.getQuestion(testByNum);
		
		model.addAttribute("test", testByNum);
		model.addAttribute("subList", subList);
		model.addAttribute("allQuestion", allQuestion);
		
		return "testUpdate";
	}
	
	// 시험지 수정하기 DB 연결
	@PostMapping("/testUpdate")
	public String testUpdateSubmit(@ModelAttribute("UpdateTest") Test test, HttpSession session) {
		
		// testAdd 페이지에서 넘어온 mem_id 값 변경(mem_nickName -> mem_id)
		Member member = (Member) session.getAttribute("member");
		test.setMem_id(member.getMem_id());
		
		// Update: 기존 데이터 보존을 위해 Insert 작업 진행
		testService.setUpdateTest(test);
		
		return "redirect:/test/testAll";
	}
	
	// 시험지 삭제하기
	@GetMapping("/testDelete")
	public String testDelete(@RequestParam("Num") Integer test_num) {
		
		// Delete: 기존 데이터 보존을 위해 visible 컬럼값 변경 작업으로 진행함
		testService.setDeleteTest(test_num);
		
		return "redirect:/test/testAll";
	}
	
	// 시험지 상세보기
	@GetMapping("/testOneView")
	public String testOneView(@RequestParam("Num") Integer test_num, Model model) {
		
		// Test 1개 상세보기
		Test test = testService.getOneTestList(test_num);
		
		model.addAttribute("test", test);
		
		return "testOneView";
	}
	
	//시험시작하기 버튼을 클릭 AJAX
	@RequestMapping(value="/callQuestion", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> callQuestion(@RequestParam("test_num") Integer test_num, HttpServletRequest req) {
		
		Map<String, Object> rusultMap = new HashMap<>();
		
	    HttpSession session=req.getSession(false);	   
	    Member member=(Member)session.getAttribute("member");
	    String mem_id=member.getMem_id();
	    // 정리노트 생성
	    fnoteservice.note_create(mem_id, test_num);
	      
 	    // Test 1개 상세보기
 	    Test test = testService.getOneTestList(test_num);
	    // serial(과목챕터코드) 해당하는 문제를 Question 테이블에서 가져오기
	    List<Question> allQuestion = testService.getQuestion(test);
	    
	    rusultMap.put("allQuestion", allQuestion);
	   
	    return rusultMap;
	}
	
	// 시험지 상세보기 비밀번호 입력 AJAX
	@RequestMapping(value="/testValue", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> testValue(@RequestParam("password") String password, @RequestParam("test_num") Integer test_num) {
        
		Map<String, Object> rusultMap = new HashMap<>();
		
		// test_num에 해당하는 행 값을 가져오기
		Test test = testService.getTestValue(test_num);
		
		// 입력 비밀번호 검사
		if (test != null && test.getTest_pw().equals(password)) {
			rusultMap.put("success", true);
	        rusultMap.put("test_num", test_num);
	    } else {
	    	rusultMap.put("success", false);
	    }
	
		return rusultMap;
	}
	
	// 시험지 과목+챕터 select 작동 AJAX
	@RequestMapping(value="/subValue", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> subValue(@RequestParam Map<String, Object> params) {
        
		Map<String, Object> rusultMap = new HashMap<>();
		
		String sub_name = (String)params.get("sub_name");
		
		// 과목명으로 모든 정보 가져오기
		List<Subject> allSubject = subjectService.getSubByName(sub_name);
		
		rusultMap.put("chapList", allSubject);
	    
		return rusultMap;
	}
	
	// 과목+챕터 선택시 해당 문제 출력 AJAX
	@RequestMapping(value="/qnaSelectRead", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> qnaSelectRead(@RequestParam Map<String, Object> params) {
        
		Map<String, Object> resultMap = new HashMap<>();
		List<Object> qnaList = new ArrayList<Object>();
		 
		// AJAX에서 List 형태로 넘어올때 받는 코드
		String json = params.get("paramList").toString();
		String serials = params.get("existingSerials").toString();
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, Object>> paramList = null;
		List<Map<String, Object>> serialList = null;
		String serial = "";

	    try {
	    	String selectedSubject = (String)params.get("selectedSubject");
	    	
	    	serialList = mapper.readValue(serials, new TypeReference<ArrayList<Map<String, Object>>>(){});
			paramList = mapper.readValue(json, new TypeReference<ArrayList<Map<String, Object>>>(){});
			
			// serial 문자로 가공해서 보내기
			for(int j = 0; j < serialList.size(); j++) {
				if (j == 0) {
					serial += (String) serialList.get(j).get("serial");
				} else {
					serial += "\',\'" + (String) serialList.get(j).get("serial");
				}
			}
			
			for(int i = 0; i < paramList.size(); i++) {

				String sub_chap = (String)paramList.get(i).get("name");
				String subCodeSum = subjectService.sub_code_sum(selectedSubject, sub_chap);
				
				// Question 테이블 sub_code_sum(과목챕터코드)=subCodeSum 일치하는 모든 값 가져오기
				List<Question> questionValue = testService.qnaSelectValue(subCodeSum, serial);
				qnaList.add(questionValue);
				
				// sub_code_sum(과목챕터코드) 값에 해당하는 question_ans(정답) 값을 Question 테이블에서 가져오기
				List<String[]> answerValue = testService.ansSelectValue(subCodeSum);				
				qnaList.add(answerValue);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	    resultMap.put("qnaList", qnaList);	
	    
		return resultMap;
	}
	
	// 검색 버튼 클릭 AJAX
    @RequestMapping(value="/search", method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> search(@RequestParam String searchType, @RequestParam String searchText, @RequestParam(required = false) Integer pageNumber ,HttpSession session) {
    	
        Map<String, Object> resultMap = new HashMap<>();
        List<Member> nickName = new ArrayList<Member>();
        Member loginCheck = (Member) session.getAttribute("member");
        
        // 로그인 여부 확인
        String sessionId = loginCheck != null ? loginCheck.getMem_id() : null;
        
        try {
        	// 페이징 처리
        	int limit = 5;
        	int total_page = 0;
        	
        	if(pageNumber == null)
    		{
        		pageNumber = 1;
    		}
        	
        	// 검색 결과 갯수
        	int total_record = testService.searchListCount(searchType, searchText);
            // 검색 결과 데이터
            List<Test> searchResults = testService.search(searchType, searchText, sessionId, pageNumber, limit);
            
            // 시험지 작성자 닉네임 확인
            for(int i = 0; i < searchResults.size(); i++) {
    			String mem_id = searchResults.get(i).getMem_id();
    			Member member = memberservice.getMyInfo(mem_id);
    			nickName.add(member);
    		}
            
        	
        	if (total_record % limit == 0){     
    	     	total_page = total_record/limit;
    	     	Math.floor(total_page);  
    		}
    		else{
    		   total_page = total_record/limit;
    		   Math.floor(total_page); 
    		   total_page =  total_page + 1; 
    		}	
        	
            
            resultMap.put("success", true);
            resultMap.put("searchResults", searchResults);
            resultMap.put("nickName", nickName);
            resultMap.put("total_page", total_page);
            resultMap.put("pageNumber", pageNumber);
            resultMap.put("total_record", total_record);
                        
        } catch (Exception e) {
        	resultMap.put("success", false);
        	resultMap.put("message", "검색 중 오류가 발생했습니다.");
        }
        
        return resultMap;
    }
    
    // 로그인 여부 확인 AJAX
    @RequestMapping(value="/loginCheck", method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> loginCheck(HttpSession session) {
    	
        Map<String, Object> resultMap = new HashMap<>();
        
        Member loginCheck = (Member)session.getAttribute("member"); 
	    if (loginCheck == null) {
	    	resultMap.put("success", false);
	    } else {
	    	resultMap.put("success", true);
	    }
        
        return resultMap;
    }	
}
