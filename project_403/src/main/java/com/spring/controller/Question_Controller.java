package com.spring.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.domain.Question;
import com.spring.domain.Subject;
import com.spring.service.Question_Service;
import com.spring.service.Subject_Service;

@Controller
@RequestMapping("/Q")
public class Question_Controller {
	
	@Autowired
	private Question_Service questionService;
	
	@Autowired
	private Subject_Service subjectService;
	
	//question 메인 페이지 임시로 지정함. 추후 수정 예정
	@RequestMapping("/main")
	public String temporary() {
		return "Question_main";
	}
	
	//객관식 문제 작성 폼 페이지로 이동
	@GetMapping("/Q_addMCQ")
	public String Q_addMCQ_form(@ModelAttribute Question question, Model model) {
		System.out.println("==========================================");
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
		//폼 페이지에서 select한 과목과 챕터를 가져와 DB에서 일치하는 DTO를 가져오는 작업
		String sub_name = request.getParameter("name_select");
		String sub_chap = request.getParameter("chap_select");
		
		question.setSub_code_sum(sub_code_sum(sub_name, sub_chap));
		//정답의 쉼표를 다른 문자로 변환
		question.setQuestion_ans(question.getQuestion_ans().replace(",", "|★|"));
		System.out.println("컨트롤러 | Q_addMCQ() 정답 : "+question.getQuestion_ans());
		
		//ModelAttribute로 데이터를 받은 DTO에서 이미지 파일을 처리
		if(question.getQuestion_img().getSize()!=0) {
			img_file_processing(question, request);
		}else {
			System.out.println("컨트롤러 | Q_addMCQ() 이미지 파일이 없음");
		}
		//서비스 이동
		System.out.println("컨트롤러 | addMCQ() 호출");
		questionService.addMCQ(question);
		
		return "Question_view";
	}
	
	//주관식 문제 작성 폼 페이지로 이동
	@GetMapping("/Q_addSAQ")
	public String Q_addSAQ_form(@ModelAttribute Question question, Model model) {
		System.out.println("==========================================");
		System.out.println("컨트롤러 | Q_addSAQ() 도착");
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
		//선택한 과목과 챕터를 가져와 변수에 넣고 과목코드를 만드는 함수에 파라미터로 넘김
		String sub_name = request.getParameter("name_select");
		String sub_chap = request.getParameter("chap_select");
		
		question.setSub_code_sum(sub_code_sum(sub_name, sub_chap));
		
		//이미지 파일 처리
		if(question.getQuestion_img().getSize()!=0) {
			img_file_processing(question, request);
		}else {
			System.out.println("컨트롤러 | Q_addSAQ() 이미지 파일이 없음");
		}
		
		//서비스 이동
		System.out.println("컨트롤러 | addSAQ() 호출");
		questionService.addSAQ(question);
		
		return "Question_view";
	}
	
	//코딩 문제를 만드는 폼 페이지로 이동
	@GetMapping("/Q_addCP")
	public String Q_addCP_form(@ModelAttribute Question question, Model model){
		System.out.println("==========================================");
		System.out.println("컨트롤러 | Q_addCP_form() 도착");
		ArrayList<Subject> sub_all = subjectService.getSubAll();
		ArrayList<Subject> sub_all_name = subjectService.getSubAllName();
		model.addAttribute("sub_all", sub_all);
		model.addAttribute("sub_all_name", sub_all_name);
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
		
		//문제 내용과 코드 내용 합치기
		String text = request.getParameter("question_content_text");
		question.setQuestion_content(text+"|★|"+question.getQuestion_content());
		System.out.println("컨트롤러 | Q_addCP() 문제 내용 : "+question.getQuestion_content());
		
		
		//과목 코드 만들기
		String sub_name = request.getParameter("name_select");
		String sub_chap = request.getParameter("chap_select");
		
		question.setSub_code_sum(sub_code_sum(sub_name, sub_chap));
		
		//이미지 파일 처리
		if(question.getQuestion_img().getSize()!=0) {
			img_file_processing(question, request);
		} else {
			System.out.println("컨트롤러 | Q_addCP() 이미지 파일 없음.");
		}
		
		//서비스 이동
		System.out.println("컨트롤러 | addCP() 호출");
		questionService.addCP(question); 
		
		return "Question_view";
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
 	public String Q_all(Model model){
		System.out.println("==========================================");
		System.out.println("컨트롤러 | Q_all() 도착");
		ArrayList<Question>	question_all = questionService.getQuestionAll();
		ArrayList<Subject> sub_all_name = subjectService.getSubAllName();
		model.addAttribute("question_all", question_all);
		model.addAttribute("sub_all_name", sub_all_name);
		return "Question_all";
	}
	
	//sub_code_sum과 일치하는 Question을 찾아 ArrayList에 담고 맵으로 리턴하는 함수
	@ResponseBody
	@PostMapping("/Q_search")
	public HashMap<String, Object> Q_search(@RequestBody HashMap<String, Object> map){
		System.out.println("==========================================");
		System.out.println("컨트롤러 | Q_search() 도착");
		//Map에 저장된 sub_name과 sub_chap을 꺼내 과목코드로 변환
		String sub_code = sub_code_sum((String)map.get("name"), (String)map.get("chap"));

		//변환된 코드를 가지고 Question_Repository로 이동
		ArrayList<Question> question = questionService.getQuestionsBySubCode(sub_code);
		
		HashMap<String, Object> search = new HashMap<String, Object>();
		search.put("question", question);
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
		System.out.println(question.getQuestion_count());
		
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
		
		String[] ans = question.getQuestion_content().split("\\|★\\|");
		System.out.println(ans[1]);
		model.addAttribute("ans", ans);
		model.addAttribute("question", question);
		System.out.println("컨트롤러 | Q_readCP() 뷰로 이동");
		return "Question_CP_view";
	}
	
	//요청 파라미터로 question_serial, question_count, question_plus을 받아 전처리 후 DB로 가져가는 함수
	@GetMapping("/Q_plusCount")
	public String Q_plusCount(HttpServletRequest request){
		System.out.println("==========================================");
		System.out.println("컨트롤러 | Q_plusCount() 도착");
		//전처리
		String question_serial = request.getParameter("serial");
		int question_count = Integer.parseInt(request.getParameter("count"));
		int question_plus = Integer.parseInt(request.getParameter("plus"));
		question_count+=question_plus;
		//System.out.println(question_serial+"|"+question_count+"|"+question_plus);
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
        HashMap<String, Object> return_map = new HashMap<>();

        try {
            String fileName = "test.java";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                writer.write(code);
            }

            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            int result = compiler.run(null, null, null, fileName);

            if (result == 0) {
            	Process process = Runtime.getRuntime().exec("java test");
            	BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));

                StringBuilder output = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line);
                }
                return_map.put("success", true);
                return_map.put("output", output.toString());
            } else {
                // 컴파일 에러 발생
                StringBuilder errorOutput = new StringBuilder();
                errorOutput.append("컴파일 에러 발생 (상태 코드: ").append(result).append("):\n");

                // Diagnostics 수집
                DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
                try (StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null)) {
                    Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjects(fileName);
                    compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits).call();
                }

                for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                    errorOutput.append("오류: ").append(diagnostic.getMessage(null)).append("\n")
                               .append("행: ").append(diagnostic.getLineNumber()).append("\n");
                }
                return_map.put("success", false);
                return_map.put("output", errorOutput.toString());
                return_map.put("errorCode", result); // 오류 코드 추가
            }
        } catch (Exception e) {
        	return_map.put("output", "오류 : " + e.getMessage());
        }

        return return_map; // 결과 반환
    }
	
	//question_serial과 일치하는 Question DTO를 가지고 수정 페이지로 이동하는 함수
	@GetMapping("/Q_updateMCQ/{question_serial}")
	public String Q_updateMCQ_form(
			@PathVariable String question_serial, 
			@ModelAttribute Question question, 
			Model model) {
		System.out.println("==========================================");
		System.out.println("컨트롤러 | Q_updateMCQ_form() 도착");
		//question_serial을 통해 Question DTO를 가지고온 후에 model에 추가한다.
		question = questionService.getQuestionBySerial(question_serial);
		String[] ans = question.getQuestion_ans().split("\\|★\\|");
		question.setQuestion_ans(ans[4]);
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
			img_file_processing(question, request);
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
			Model model){
		System.out.println("==========================================");
		System.out.println("컨트롤러 | Q_updateSAQ_form() 도착");
		//Question DTO를 구한 뒤에 model에 추가
		question = questionService.getQuestionBySerial(question_serial);
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
			img_file_processing(question, request);
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
			Model model){
		System.out.println("==========================================");
		System.out.println("컨트롤러 | Q_updateCP() 도착");
		//Question DTO를 구한 뒤에 전처리
		question = questionService.getQuestionBySerial(question_serial);
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
			img_file_processing(question, request);
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
		System.out.println("컨트롤러 | Q_delete 도착");
		questionService.visibleQuestion(question_serial);
		return "redirect:/Q/Q_all";
	}
	
	//선택된 과목의 고유 넘버를 만드는 함수로 모듈화 하였음.
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
		System.out.println("컨트롤러 | sub_code_sum 값 : "+sub_code_sum);
		return sub_code_sum;
	}
	
	//이미지 파일을 처리하는 함수로 다른 문제에도 사용되는 경우가 많을 것이라 판단해서 따로 모듈화를 진행했음.
	private void img_file_processing(Question question, HttpServletRequest request) {
		System.out.println("컨트롤러 | img_file_processing() 도착");
		//파일 저장 경로
		String path = request.getServletContext().getRealPath("/resources/images");
		System.out.println("컨트롤러 | 이미지파일 저장 경로 : "+path);
		//파일 이름 만들기
		//파일의 확장자를 분리하는 작업
		MultipartFile multi = question.getQuestion_img();
		String file_name = multi.getOriginalFilename();
		String[] file_names = file_name.split("\\.");
		String extension = file_names[file_names.length-1];
		//파일의 이름을 현재시간으로 사용하기 위한 작업
		long time = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmssSSS");
		//결합 : RLPL209912311030123.확장자
		String img_name = "RLPL"+sdf.format(time)+"."+extension;
		System.out.println("저장되는 파일 : "+img_name);
		//DTO에 SET
		question.setQuestion_img_name(img_name);
		
		//빈 파일 생성
		File file = new File(path, img_name); 
	
		//빈 파일에 작성
		try {
			multi.transferTo(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
