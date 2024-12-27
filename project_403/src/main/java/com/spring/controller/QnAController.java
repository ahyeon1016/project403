package com.spring.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.domain.Member;
import com.spring.domain.QnA;
import com.spring.service.Favorite_Service;
import com.spring.service.MemberService;
import com.spring.service.QnA_Service;

@Controller
@RequestMapping("/QnA")
public class QnAController {
	
	@Autowired
	QnA_Service qnaService;
	
	@Autowired
	MemberService memberService;

	@Autowired
	Favorite_Service favoriteService;
	
	@RequestMapping("/main")
	public String main() {
		return "QnA_main";
	}
	
	//comment_root작성 페이지로 이동
	@GetMapping("/addCommentRoot")
	public String addCommentRoot_form(
			@RequestParam String question_serial, 
			@ModelAttribute("qna") QnA qna) {
		System.out.println("==========================================");
		System.out.println("컨트롤러 | addCommentRoot_form()도착");
	    qna.setQuestion_serial(question_serial);
		return "QnA_addCommentRoot_form";
	}
	
	//폼 페이지에서 작성된 comment_root 정보를 가지고 DB로 이동 
	@PostMapping("/addCommentRoot")
	public String addCommentRoot(
			@ModelAttribute("qna") QnA qna,
			HttpServletRequest request) {
		System.out.println("==========================================");
		System.out.println("컨트롤러 | addCommentRoot() 도착");
		//세션에 있는 Member DTO를 가져와서 그 안에있는 id를 추출한다.
		HttpSession session = request.getSession(false);
		Member member = (Member)session.getAttribute("member");
		String mem_id = member.getMem_id();
		 
		//qna와 mem_id를 가지고 DB로 이동
		qnaService.addCommentRoot(qna, mem_id);
		
		return "redirect:/QnA/main";
	}
	
	//모든 comment_root정보를 가져오기 위해 DB로 이동하는 함수
	@GetMapping("/commentRootAll")
	public String getCommentRootAll(
			Model model, 
			@RequestParam int page) {
		System.out.println("==========================================");
		System.out.println("컨트롤러 | getCommentRootAll() 도착");
		
		//DB로 이동하여 모든 comment_root 정보를 가져옴
		ArrayList<QnA> rootAll = qnaService.getCommentRootAll();		
		
		//mem_id를 통해 mem_nickName을 구하고 QnA DTO의 변수에 설정한다.
		System.out.println("컨트롤러 | 작성자의 nickName을 구하기 위해 memberService로 이동");
		for(QnA qna : rootAll) {
			Member member = memberService.getMyInfo(qna.getMem_id());
			qna.setMem_nickName(member.getMem_nickName());
			//좋아요 싫어요의 갯수를 QnA DTO의 변수에 담는다.
			int qnaNum = qna.getComment_num();
			int totalGood = favoriteService.getTotalGood(qnaNum);
			qna.setComment_totalGood(totalGood);
			int totalBad = favoriteService.getTotalBad(qnaNum);
			qna.setComment_totalBad(totalBad);
		}
		
		//페이지 처리
		int index = ((page - 1) * 5); 		// 시작 인덱스를 계산
		int maxPage = 5*page; 				// 최대 페이지 수를 계산
		if( !(maxPage<=rootAll.size()) ){	// maxPage값이 List의 size를 넘어가지 않게 처리
			maxPage=rootAll.size();
		}
		int totalPage;						// 페이징 처리를 위한 페이지의 갯수 계산
		if(rootAll.size()%5==0) {
			totalPage=rootAll.size()/5;
		}else {
			totalPage=(rootAll.size()/5)+1;
		}
		
		//모델에 저장		
		model.addAttribute("rootAll", rootAll);
		model.addAttribute("index", index);
		model.addAttribute("maxPage", maxPage);
		model.addAttribute("totalPage", totalPage);
		
		return "QnA_commentRootAll";
	}
	
	//comment_root와 일치하는 DTO를 가져오기 위해 DB로 이동하는 함수
	@GetMapping("/commentRootOne")
	public String getCommentRootOne(
			@RequestParam int comment_root,
			Model model,
			HttpServletRequest request) {
		System.out.println("==========================================");
		System.out.println("컨트롤러 | getCommentRootOne() 도착");
		//comment_root를 가지고 QnA DTO를 가져온다.
		QnA qna = qnaService.getCommentRootOne(comment_root);
		
		//좋아요 싫어요의 갯수를 QnA DTO의 변수에 담는다.
		int qnaNum = qna.getComment_num();
		int totalGood = favoriteService.getTotalGood(qnaNum);
		qna.setComment_totalGood(totalGood);
		int totalBad = favoriteService.getTotalBad(qnaNum);
		qna.setComment_totalBad(totalBad);

		//세션 연결
		HttpSession session = request.getSession(false);
		Member user = (Member)session.getAttribute("member");
		
		//세션에 저장된 Member DTO의 mem_id가 해당 질문에 좋아요를 눌렀는지 확인하기 위함
		Boolean isGood_btn = null;
		if(user != null) {
			String user_id = user.getMem_id();
			isGood_btn = favoriteService.isGoodClicked(user_id, qnaNum);
		}else {
			isGood_btn = false;
		}
		
		//isGood_btn의 값에 따라 goodColor의 값을 설정
		String goodColor = "";
		if(isGood_btn) {
			goodColor = "gray";
		}else {
			goodColor = "white";
		}
		
		//세션에 저장된 Member DTO의 mem_id가 해당 질문에 싫어요를 눌렀는지 확인하기 위함
		Boolean isBad_btn = null;
		if(user != null) {
			String user_id = user.getMem_id();
			isBad_btn = favoriteService.isBadClicked(user_id, qnaNum);
		}else {
			isBad_btn = false;
		}
		
		//isGood_btn의 값에 따라 goodColor의 값을 설정
		String badColor = "";
		if(isBad_btn) {
			badColor = "gray";
		}else {
			badColor = "white";
		}
		//mem_id를 통해 mem_nickName을 구하고 QnA DTO의 변수에 설정한다.
		System.out.println("컨트롤러 | 작성자의 nickName을 구하기 위해 memberService로 이동");
		if(qna!=null) {
			Member member = memberService.getMyInfo(qna.getMem_id());
			qna.setMem_nickName(member.getMem_nickName());
		}
		
		model.addAttribute("qna", qna);
		model.addAttribute("isGood_btn", !isGood_btn);	//View에서 함수의 파라미터로 사용하기 때문에 !을 사용
		model.addAttribute("goodColor", goodColor);
		model.addAttribute("isBad_btn", !isBad_btn);	//View에서 함수의 파라미터로 사용하기 때문에 !을 사용
		model.addAttribute("badColor", badColor);
		return "QnA_commentRoot";
	}

	//comment_parent를 추가하기 위해 ajax로 데이터를 받아 처리하는 함수
	@ResponseBody
	@PostMapping("/addCommentParent")
	public HashMap<String, Object> addCommentParent(
			@RequestBody HashMap<String, Object> map,
			HttpServletRequest request){
		System.out.println("==========================================");
		System.out.println("컨트롤러 | addCommentParent() 도착");
		System.out.println("comment_parent를 작성한 유저 : "+map.get("mem_id"));
		System.out.println("comment_parent를 작성한 root번호 : "+map.get("comment_root"));
		System.out.println("comment_parent의 내용 : "+map.get("comment_content"));
		//세션에서 Member DTO를 꺼낸 뒤에 mem_id을 map에 추가한다.
		HttpSession session = request.getSession(false);
		Member member = (Member) session.getAttribute("member");
		String mem_id = member.getMem_id();
		
		map.put("mem_id", mem_id);
		map = qnaService.addCommentParent(map);		
		
		return map;
	}
	
	//페이지가 로딩될 때 그 페이지의 comment_root 정보를 가지고 데이터 베이스로 이동하는 함수
	@ResponseBody
	@PostMapping("/getCommentParent")
	public HashMap<String, ArrayList<QnA>> getCommentParent(
			@RequestBody HashMap<String, Object> map){
		System.out.println("==========================================");
		System.out.println("컨트롤러 | getCommentParent() 도착");
		System.out.println(map.get("comment_root"));
		HashMap<String, ArrayList<QnA>> returnMap = qnaService.getCommentParent((Integer)map.get("comment_root"));
		
		//mem_id를 통해 mem_nickName을 구하고 ArrayList안에 있는 QnA DTO의 변수에 설정한다.
		System.out.println("컨트롤러 | 작성자의 nickName을 구하기 위해 memberService로 이동");
		for(QnA qna : returnMap.get("list")) {
			Member member = memberService.getMyInfo(qna.getMem_id());
			qna.setMem_nickName(member.getMem_nickName());
		}
		return returnMap;
	}
	
	//ajax로 mem_id, root, parent, question_serial, child 데이터를 받아 DB로 이동하는 함수
	@ResponseBody
	@PostMapping("/addCommentChild")
	public HashMap<String, Object> addCommentChild(
			@RequestBody HashMap<String, Object> map, 
			HttpServletRequest request){
		System.out.println("==========================================");
		System.out.println("컨트롤러 | addCommentChild() 도착");
		//세션에서 Member DTO를 꺼낸 뒤에 mem_id을 map에 추가한다.
		HttpSession session = request.getSession(false);
		Member member = (Member) session.getAttribute("member");
		String mem_id = member.getMem_id();
		
		map.put("mem_id", mem_id);
		map = qnaService.addCommentChild(map);
		return map;
	}

	//ajax로 parent를 지우기 위한 데이터를 받고 DB로 이동하는 함수
	@ResponseBody
	@PostMapping("removeCommentParent")
	public HashMap<String, Object> removeCommentParent(
			@RequestBody HashMap<String, Object> map, 
			HttpServletRequest request){
		System.out.println("==========================================");
		System.out.println("컨트롤러 | removeCommentParent() 도착");
		//세션에서 Member DTO를 꺼낸 뒤에 mem_id을 map에 추가한다.
		HttpSession session = request.getSession(false);
		Member member = (Member) session.getAttribute("member");
		String mem_id = member.getMem_id();
		
		map.put("mem_id", mem_id);
		map = qnaService.removeCommentParent(map);
		
		return map;
	}
	
	//ajax로 child를 지우기 위한 데이터를 받고 DB로 이동하는 함수
	@ResponseBody
	@PostMapping("removeCommentChild")
	public HashMap<String, Object> removeCommentChild(
			@RequestBody HashMap<String, Object> map, 
			HttpServletRequest request){
		System.out.println("==========================================");
		System.out.println("컨트롤러 | removeCommentChild() 도착");
		//세션에서 Member DTO를 꺼낸 뒤에 mem_id을 map에 추가한다.
		HttpSession session = request.getSession(false);
		Member member = (Member) session.getAttribute("member");
		String mem_id = member.getMem_id();
		
		map.put("mem_id", mem_id);
		map = qnaService.removeCommentChild(map);
		
		return map;
	}

}
