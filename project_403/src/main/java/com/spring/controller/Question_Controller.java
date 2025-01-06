package com.spring.controller;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.commons.compiler.CompileException;
import org.codehaus.janino.SimpleCompiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.domain.Member;
import com.spring.domain.Question;
import com.spring.domain.Subject;
import com.spring.service.MemberService;
import com.spring.service.Question_Service;
import com.spring.service.Subject_Service;

@Controller
@RequestMapping("/Q")
public class Question_Controller {	
	@Autowired
	private Question_Service questionService;
	
	@Autowired
	private Subject_Service subjectService;
	
	@Autowired
	private MemberService memberService;
	
	//객관식 문제 작성 폼 페이지로 이동
	@GetMapping("/Q_addMCQ")
	public String Q_addMCQ_form(@ModelAttribute Question question, Model model) {
		System.out.println("==========================================");
		//과목과 챕터를 선택하기 위해 subject를 가져옴.
		ArrayList<Subject> sub_all = subjectService.getSubAll();
		ArrayList<Subject> sub_all_name = subjectService.getSubAllName();
		model.addAttribute("sub_all", sub_all);
		model.addAttribute("sub_all_name", sub_all_name);
		System.out.println("컨트롤러 | Question_addMCQ_form로 이동");
		return "Question_addMCQ_form";
	}
	
	//Question_addMCQ_form 페이지에서 입력받은 정보를 받아 처리 후에 DAO에 전달
	@PostMapping("/Q_addMCQ")
	public String Q_addMCQ(@ModelAttribute Question question, HttpServletRequest request) {
		System.out.println("==========================================");
		System.out.println("컨트롤러 | Q_addMCQ() 도착");
		//전처리
		//세션에서 작성자의 member DTO를 가져와 mem_serial을 구한다.
		HttpSession session = request.getSession(false);
		Member member = (Member)session.getAttribute("member");
		int mem_serial = member.getMem_serial();
		
		//폼 페이지에서 select한 과목과 챕터를 가져와 DB에서 일치하는 DTO를 가져오는 작업
		String sub_name = request.getParameter("name_select");
		String sub_chap = request.getParameter("chap_select");
		question.setSub_code_sum(subjectService.sub_code_sum(sub_name, sub_chap));
		
		//정답의 쉼표를 다른 문자로 변환
		question.setQuestion_ans(question.getQuestion_ans().replace(",", "|★|"));
		System.out.println("컨트롤러 | Q_addMCQ() 정답 : "+question.getQuestion_ans());
		
		//ModelAttribute로 데이터를 받은 DTO에서 이미지 파일을 처리
		if(question.getQuestion_img().getSize()!=0) {
			questionService.img_file_processing(question, request);
		}else {
			System.out.println("컨트롤러 | Q_addMCQ() 이미지 파일이 없음");
		}
		
		//서비스 이동
		System.out.println("컨트롤러 | addMCQ() 호출");
		questionService.addMCQ(question, mem_serial);
		
		return "redirect:/Q/Q_all";
	}
	
	//주관식 문제 작성 폼 페이지로 이동
	@GetMapping("/Q_addSAQ")
	public String Q_addSAQ_form(@ModelAttribute Question question, Model model) {
		System.out.println("==========================================");
		System.out.println("컨트롤러 | Q_addSAQ() 도착");
		//과목과 챕터를 선택하기 위해 subject를 가져옴.
		ArrayList<Subject> sub_all = subjectService.getSubAll();
		ArrayList<Subject> sub_all_name = subjectService.getSubAllName();
		model.addAttribute("sub_all", sub_all);
		model.addAttribute("sub_all_name", sub_all_name);
		return "Question_addSAQ_form";
	}
	
	//Question_addSAQ_form에서 받은 문제 정보를 받아 처리한 후에 DAO에 전달
	@PostMapping("/Q_addSAQ")
	public String Q_addSAQ(@ModelAttribute Question question, HttpServletRequest request) {
		System.out.println("==========================================");
		System.out.println("컨트롤러 | Q_addSAQ() 도착");
		//전처리
		//세션에서 작성자의 member DTO를 가져와 mem_serial을 구한다.
		HttpSession session = request.getSession(false);
		Member member = (Member)session.getAttribute("member");
		int mem_serial = member.getMem_serial();
		
		//선택한 과목과 챕터를 가져와 변수에 넣고 과목코드를 만드는 함수에 파라미터로 넘김
		String sub_name = request.getParameter("name_select");
		String sub_chap = request.getParameter("chap_select");
		question.setSub_code_sum(subjectService.sub_code_sum(sub_name, sub_chap));
		
		//이미지 파일 처리
		if(question.getQuestion_img().getSize()!=0) {
			questionService.img_file_processing(question, request);
		}else {
			System.out.println("컨트롤러 | Q_addSAQ() 이미지 파일이 없음");
		}
		
		//서비스 이동
		System.out.println("컨트롤러 | addSAQ() 호출");
		questionService.addSAQ(question, mem_serial);
		
		return "redirect:/Q/Q_all";
	}
	
	//코딩 문제를 만드는 폼 페이지로 이동
	@GetMapping("/Q_addCP")
	public String Q_addCP_form(@ModelAttribute Question question, Model model){
		System.out.println("==========================================");
		System.out.println("컨트롤러 | Q_addCP_form() 도착");
		//과목과 챕터를 선택하기 위해 subject를 가져옴.
		ArrayList<Subject> sub_all = subjectService.getSubAll();
		ArrayList<Subject> sub_all_name = subjectService.getSubAllName();
		model.addAttribute("sub_all", sub_all);
		model.addAttribute("sub_all_name", sub_all_name);
		//코드 입력 양식 작성
		String content 
				= "public class test {\n"
				+ "\n"
				+ "    public static void main(String[] args){\n"
				+ "        int a = 0;\n"
				+ "        System.out.print();\n"
				+ "    }\n"
				+ "}";
		System.out.println(content);
		question.setQuestion_content(content);
		return "Question_addCP_form";
	}
	
	//코딩 문제를 만드는 폼 페이지로 이동
	@PostMapping("/Q_addCP")
	public String Q_addCP(@ModelAttribute Question question, HttpServletRequest request){
		System.out.println("==========================================");
		System.out.println("컨트롤러 | Q_addCP() 도착");
		System.out.println("컨트롤러 | 작성한 문제 내용 : \n"+question.getQuestion_content());
		//전처리
		//세션에서 작성자의 member DTO를 가져와 mem_serial을 구한다.
		HttpSession session = request.getSession(false);
		Member member = (Member)session.getAttribute("member");
		int mem_serial = member.getMem_serial();

		//문제 내용과 코드 내용 합치기
		String text = request.getParameter("question_content_text");
		question.setQuestion_content(text+"|★|"+question.getQuestion_content());
		System.out.println("컨트롤러 | Q_addCP() 문제 내용 : "+question.getQuestion_content());
		
		//과목 코드 만들기
		String sub_name = request.getParameter("name_select");
		String sub_chap = request.getParameter("chap_select");
		question.setSub_code_sum(subjectService.sub_code_sum(sub_name, sub_chap));
		
		//이미지 파일 처리
		if(question.getQuestion_img().getSize()!=0) {
			questionService.img_file_processing(question, request);
		} else {
			System.out.println("컨트롤러 | Q_addCP() 이미지 파일 없음.");
		}
		//서비스 이동
		System.out.println("컨트롤러 | addCP() 호출");
		questionService.addCP(question, mem_serial); 
		
		return "redirect:/Q/Q_all";
	}
	
	//문제 작성 페이지에서 sub_name에 해당하는 sub_chap을 가져오는 함수
	@ResponseBody
	@PostMapping("/Q_subNameByChap")
	public HashMap<String, Object> Q_subNameByChap(@RequestBody HashMap<String, Object> map){
		System.out.println("==========================================");
		System.out.println("컨트롤러 | Q_subNameByChap() 도착");
		HashMap<String, Object> a = new HashMap<String, Object>();
		return subjectService.subNameByValue(map);
	}
	
	//모든 문제를 확인할 수 있는 페이지로 이동
	@GetMapping("/Q_all")
 	public String Q_all(Model model, HttpServletRequest request){
		System.out.println("==========================================");
		System.out.println("컨트롤러 | Q_all() 도착");
		HttpSession session = request.getSession(false);
		Member member = (Member) session.getAttribute("member");
		if(member==null) {
			System.out.println("컨트롤러 | Q_all() 로그인 페이지로 이동");
			return "redirect:/member/login";
		}
		//question에서 mem_serial을 통해 mem_nickName의 값을 설정한 후에 model에 담는다.
		ArrayList<Subject> sub_all_name = subjectService.getSubAllName();
		
		model.addAttribute("sub_all_name", sub_all_name);
		return "Question_all";
	}
	
	//sub_code_sum과 일치하는 Question을 찾아 ArrayList에 담고 맵으로 리턴하는 함수
	@ResponseBody
	@PostMapping("/Q_search")
	public HashMap<String, Object> Q_search(
			@RequestBody HashMap<String, Object> map, 
			HttpServletRequest request){
		System.out.println("==========================================");
		System.out.println("컨트롤러 | Q_search() 도착");
		//Session에 저장되어 있는 사용자의 정보를 꺼낸다.
		HttpSession session = request.getSession(false);
		int mem_serial = 0;
		if(session!=null) {
			Member member = (Member) session.getAttribute("member");
			mem_serial = member.getMem_serial();
		}
		//Map에 저장된 sub_name과 sub_chap을 꺼내 과목코드로 변환
		String sub_code = subjectService.sub_code_sum((String)map.get("name"), (String)map.get("chap"));
		
		//question_id를 가져와 변수에 대입
		String id = (String)map.get("id");
		
		//사용자가 작성한 문제인지 확인한 후에 변환된 코드를 가지고 Question_Repository로 이동
		Boolean myQuestion = (Boolean)map.get("myQuestion");
		ArrayList<Question> question = null;
		if(myQuestion) {
			System.out.println("컨트롤러 | Q_search() 사용자가 작성한 문제");
			question = questionService.getMyQuestionsBySubCode(sub_code, mem_serial, id);
		}else{	
			System.out.println("컨트롤러 | Q_search() 모든 문제");
			question = questionService.getQuestionsBySubCode(sub_code, id);
		}
		
		//question에서 mem_serial을 통해 mem_nickName의 값을 설정한다.
		for(Question q : question) {
			int serial = q.getMem_serial();
			String nickName = memberService.getNickNameBySerial(serial);
			q.setMem_nickName(nickName);
		}
		
		//페이징 처리를 위한 값을 구한다.
		int page = (int)map.get("page");			
		int index = question.size();
		int totalPage = 0;
		int maxIndex = 0;
		int minIndex = 0;
		System.out.println("컨트롤러 | Q_search() page: "+page);
		
		if(index%5 == 0) {
			totalPage = index/5;
		}else {
			totalPage = (index/5)+1;
		}
		System.out.println("컨트롤러 | Q_search() totalPage: "+totalPage);
		
		if(page == 1) {
			minIndex = 0;
		}else {
			minIndex = (page*5)-5;
		}
		System.out.println("컨트롤러 | Q_search() minIndex: "+minIndex);
		
		if(totalPage==page) {
			maxIndex = index;
		}else {
			maxIndex = page*5;
		}
		System.out.println("컨트롤러 | Q_search() maxIndex: "+maxIndex);
		
		
		HashMap<String, Object> search = new HashMap<String, Object>();
		search.put("question", question);
		search.put("totalPage", totalPage);
		search.put("maxIndex", maxIndex);
		search.put("minIndex", minIndex);
		return search;
	}
	
	//파라미터로 받은 question_serial을 통해 얻은 DTO를 Model에 저장후 뷰로 이동하는 함수
	@GetMapping("/Q_readMCQ/{question_serial}")
	public String Q_readMCQ(@PathVariable String question_serial, Model model) {
		System.out.println("==========================================");
		System.out.println("컨트롤러 | Q_readMCQ() 도착");
		//파라미터로 받은 question_serial 변수를 가지고 DB로 이동
		System.out.println("컨트롤러 | Q_readMCQ() 파라미터로 받은 값을 가지고 서비스의 getQuestionBySerial()호출");
		Question question = questionService.getQuestionBySerial(question_serial);
		
		//question에서 mem_serial을 통해 mem_nickName의 값을 설정한다.
		if(question!=null) {
			int serial = question.getMem_serial();
			String nickName = memberService.getNickNameBySerial(serial);
			question.setMem_nickName(nickName);
		}
		
		//배열로 들어가있는 정답을 split 메서드로 잘라 배열에 담는다.
		String[] ans = question.getQuestion_ans().split("\\|★\\|");
		model.addAttribute("ans", ans);
		model.addAttribute("question", question);
		System.out.println("컨트롤러 | Q_readMCQ() 뷰로 이동");
		return "Question_MCQ_view";
	}
	
	//파라미터로 받은 question_serial을 통해 얻은 DTO를 Model에 저장후 뷰로 이동하는 함수
	@GetMapping("/Q_readSAQ/{question_serial}")
	public String Q_readSAQ(@PathVariable String question_serial, Model model) {
		System.out.println("==========================================");
		System.out.println("컨트롤러 | Q_readSAQ() 도착");
		//파라미터로 받은 question_serial 변수를 가지고 DB로 이동
		System.out.println("컨트롤러 | Q_readSAQ() 파라미터로 받은 값을 가지고 서비스의 getQuestionBySerial()호출");
		Question question = questionService.getQuestionBySerial(question_serial);
		
		//question에서 mem_serial을 통해 mem_nickName의 값을 설정한다.
		if(question!=null) {
			int serial = question.getMem_serial();
			String nickName = memberService.getNickNameBySerial(serial);
			question.setMem_nickName(nickName);
		}
		
		model.addAttribute("question", question);
		System.out.println("컨트롤러 | Q_readSAQ() 뷰로 이동");
		return "Question_SAQ_view";
	}
	
	//파라미터로 받은 question_serial을 통해 얻은 DTO를 Model에 저장후 뷰로 이동하는 함수
	@GetMapping("/Q_readCP/{question_serial}")
	public String Q_readCP(
			@PathVariable String question_serial, 
			Model model) {
		System.out.println("==========================================");
		System.out.println("컨트롤러 | Q_readCP() 도착");
		//파라미터로 받은 question_serial 변수를 가지고 DB로 이동
		Question question = questionService.getQuestionBySerial(question_serial);
		
		//question에서 mem_serial을 통해 mem_nickName의 값을 설정한다.
		if(question!=null) {
			int serial = question.getMem_serial();
			String nickName = memberService.getNickNameBySerial(serial);
			question.setMem_nickName(nickName);
		}
		
		//배열로 들어가있는 정답을 split 메서드로 잘라 배열에 담는다.
		String[] ans = question.getQuestion_content().split("\\|★\\|");
		System.out.println(ans[1]);
		model.addAttribute("ans", ans);
		model.addAttribute("question", question);
		System.out.println("컨트롤러 | Q_readCP() 뷰로 이동");
		return "Question_CP_view";
	}
	
	//요청 파라미터로 question_serial, question_count, plus, question_level을 받아 전처리 후 DB로 가져가는 함수
	@GetMapping("/Q_plusCount")
	public String Q_plusCount(HttpServletRequest request){
		System.out.println("==========================================");
		System.out.println("컨트롤러 | Q_plusCount() 도착");
		//전처리
		String question_serial = request.getParameter("serial");
		int question_count = Integer.parseInt(request.getParameter("count"));
		int plus = Integer.parseInt(request.getParameter("plus"));
		question_count+=plus;
		int question_level = Integer.parseInt(request.getParameter("level"));
		
		//사용자의 Member 정보에 question_level을 보내 mem_exp의 값을 증가시키는 함수
		HttpSession session = request.getSession(false);
		Member member = (Member) session.getAttribute("member");
		int point = question_level*2;
		int exp = question_level;
		String mem_id = member.getMem_id();
		memberService.member_lvup(point, exp, mem_id);
		
		//전처리한 변수를 가지고 DB로 이동
		System.out.println("컨트롤러 | 서비스의 updateQuestionCount() 호출");
		questionService.updateQuestionCount(question_serial, question_count);
		
		//문제 목록 페이지로 이동
		return "redirect:/Q/Q_all";
	}
	
	//CP 문제를 JSON으로 받아서 컴파일한 후에 결과를 리턴
	@ResponseBody
	@PostMapping("/Compile")
	public HashMap<String, Object> Compile(@RequestBody HashMap<String, Object> map){
		System.out.println("==========================================");
		System.out.println("컨트롤러 | Compile() 도착");
		System.out.println(map.get("ans_input"));
		//전처리
        String code = (String) map.get("ans_input"); // 요청에서 코드 가져오기
        HashMap<String, Object> returnMap = new HashMap<>();

        try {
            SimpleCompiler compiler = new SimpleCompiler();
            compiler.cook(code); // 코드 컴파일

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            PrintStream originalOut = System.out;
            System.setOut(printStream);

            try {
                Class<?> clazz = compiler.getClassLoader().loadClass("test");
                clazz.getDeclaredMethod("main", String[].class).invoke(null, (Object) new String[]{});
            } finally {
                System.setOut(originalOut);
            }

            returnMap.put("success", true);
            returnMap.put("output", outputStream.toString(StandardCharsets.UTF_8));
        } catch (CompileException e) {
            returnMap.put("success", false);
            returnMap.put("output", "컴파일 오류: " + e.getMessage());
        } catch (Exception e) {
            returnMap.put("success", false);
            returnMap.put("output", "실행 오류: " + e.getMessage());
        }

        return returnMap; // 결과 반환
    }
	
	//question_serial과 일치하는 Question DTO를 가지고 수정 페이지로 이동하는 함수
	@GetMapping("/Q_updateMCQ/{question_serial}")
	public String Q_updateMCQ_form(
			@PathVariable String question_serial, 
			@ModelAttribute Question question, 
			Model model,
			HttpServletRequest request) {
		System.out.println("==========================================");
		System.out.println("컨트롤러 | Q_updateMCQ_form() 도착");
		//question_serial을 통해 Question DTO를 가지고온 후에 model에 추가한다.
		question = questionService.getQuestionBySerial(question_serial);

		//작성자와 동일한 사람인지 확인 
		HttpSession session = request.getSession(false);
		Member member = (Member)session.getAttribute("member");
		if(member.getMem_serial()!= question.getMem_serial()) {
			System.out.println("작성한 멤버가 다릅니다.");
			return "redirect:/Q/Q_all";
		}

		//정답을 split 메서드로 분리하여 배열에 담은 다음에 마지막 인덱스의 값만 모델에 담는다. 
		String[] ans = question.getQuestion_ans().split("\\|★\\|");
		question.setQuestion_ans(ans[ans.length-1]);
 		model.addAttribute("ans", ans);
		model.addAttribute("question", question);
		return "Question_updateMCQ";
	}
	
	//수정된 Question DTO를 가지고 DB로 이동하는 함수
	@PostMapping("/Q_updateMCQ")
	public String Q_updateMCQ(
			@ModelAttribute Question question, 
			HttpServletRequest request) {
		System.out.println("==========================================");
		System.out.println("컨트롤러 | Q_updateMCQ() 도착");
		System.out.println(question.getQuestion_serial());
		
		//정답의 쉼표를 다른 문자로 변환
		question.setQuestion_ans(question.getQuestion_ans().replace(",", "|★|"));

		//ModelAttribute로 데이터를 받은 DTO에서 이미지 파일을 처리
		if(question.getQuestion_img().getSize()!=0) {
			questionService.img_file_processing(question, request);
		}else {
			System.out.println("컨트롤러 | Q_updateMCQ() 이미지 파일이 없음");
		}
		
		//변경된 DTO를 가지고 DB로 이동
		questionService.updateQuestion(question);
		
		return "redirect:/Q/Q_all";
	}
	
	//question_serial을 통해 Question DTO를 구하고 폼 페이지로 이동
	@GetMapping("/Q_updateSAQ/{question_serial}")
	public String Q_updateSAQ_form(
			@PathVariable String question_serial, 
			@ModelAttribute Question question,
			Model model,
			HttpServletRequest request){
		System.out.println("==========================================");
		System.out.println("컨트롤러 | Q_updateSAQ_form() 도착");
		//Question DTO를 구한다. 
		question = questionService.getQuestionBySerial(question_serial);

		//작성자와 동일한 사람인지 확인 
		HttpSession session = request.getSession(false);
		Member member = (Member)session.getAttribute("member");	
		if(member.getMem_serial()!= question.getMem_serial()) {
			System.out.println("작성한 멤버가 z다릅니다.");
			return "redirect:/Q/Q_all";
		}
		
		//model에 추가
		model.addAttribute("question", question);
		
		//업데이트 폼 페이지 이동
		return "Question_updateSAQ";
	}	
	
	//폼 페이지에서 입력받은 정보를 가지고 DB로 이동
	@PostMapping("/Q_updateSAQ")
	public String Q_updateSAQ(@ModelAttribute Question question, HttpServletRequest request) {
		System.out.println("==========================================");
		System.out.println("컨트롤러 | Q_updateSAQ() 도착");
		
		//ModelAttribute로 데이터를 받은 DTO에서 이미지 파일을 처리
		if(question.getQuestion_img().getSize()!=0) {
			questionService.img_file_processing(question, request);
		}else {
			System.out.println("컨트롤러 | Q_updateSAQ() 이미지 파일이 없음");
		}
		
		//변경된 DTO를 가지고 DB로 이동
		questionService.updateQuestion(question);
		return "redirect:/Q/Q_all";
	}
	
	//question_serial을 가지고 DB로 이동하여 일치하는 Question DTO를 가지고 온다.
	@GetMapping("/Q_updateCP/{question_serial}")
	public String Q_updateCP_form(
			@PathVariable String question_serial,
			@ModelAttribute Question question,
			Model model,
			HttpServletRequest request){
		System.out.println("==========================================");
		System.out.println("컨트롤러 | Q_updateCP() 도착");
		//Question DTO를 구한 뒤에 전처리
		question = questionService.getQuestionBySerial(question_serial);

		//작성자와 동일한 사람인지 확인 
		HttpSession session = request.getSession(false);
		Member member = (Member)session.getAttribute("member");	
		if(member.getMem_serial()!= question.getMem_serial()) {
			System.out.println("작성한 멤버가 z다릅니다.");
			return "redirect:/Q/Q_all";
		}
		
		//ans가 아닌 content를 split함
		String[] content = question.getQuestion_content().split("\\|★\\|");
		question.setQuestion_content(content[1]);
		
		//model에 DTO및 배열변수 추가
		model.addAttribute("content", content);
		model.addAttribute("question", question);
		
		//폼 페이지로 이동
		return "Question_updateCP";
	}
	
	//폼 페이지에서 입력받은 정보를 가지고 DB로 이동
	@PostMapping("/Q_updateCP")
	public String Q_updateCP(@ModelAttribute Question question, HttpServletRequest request) {
		System.out.println("==========================================");
		System.out.println("컨트롤러 | Q_updateCP() 도착");
		//content 처리
		String text = request.getParameter("question_content_text");
		question.setQuestion_content(text+"|★|"+question.getQuestion_content());
		
		//ModelAttribute로 데이터를 받은 DTO에서 이미지 파일을 처리
		if(question.getQuestion_img().getSize()!=0) {
			questionService.img_file_processing(question, request);
		}else {
			System.out.println("컨트롤러 | Q_updateCP() 이미지 파일이 없음");
		}
		
		//변경된 DTO를 가지고 DB로 이동
		questionService.updateQuestion(question);
		return "redirect:/Q/Q_all";
	}
	
	//question_serial을 가지고 DB로 이동하는 함수
	@GetMapping("/Q_delete/{question_serial}")
	public String Q_delete(@PathVariable String question_serial){
		System.out.println("==========================================");
		System.out.println("컨트롤러 | Q_delete 도착");
		questionService.visibleQuestion(question_serial);
		return "redirect:/Q/Q_all";
	}
	
}
