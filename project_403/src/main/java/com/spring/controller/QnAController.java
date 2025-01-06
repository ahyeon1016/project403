package com.spring.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import com.spring.domain.Member;
import com.spring.domain.QnA;
import com.spring.domain.Question;
import com.spring.service.Favorite_Service;
import com.spring.service.MemberService;
import com.spring.service.QnA_Service;
import com.spring.service.Question_Service;

@Controller
@RequestMapping("/QnA")
public class QnAController {
	
	@Autowired
	QnA_Service qnaService;
	
	@Autowired
	MemberService memberService;

	@Autowired
	Favorite_Service favoriteService;
	
	@Autowired
	Question_Service questionService;
	
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
		
		return "redirect:/QnA/commentRootAll?page=1";
	}
	
	//모든 comment_root정보를 가져오기 위해 DB로 이동하는 함수
	@GetMapping("/commentRootAll")
	public String getCommentRootAll(
			Model model, 
			HttpServletRequest request, 
			@RequestParam int page) {
		System.out.println("==========================================");
		System.out.println("컨트롤러 | getCommentRootAll() 도착");
		//사용자의 로그인 여부 확인
		HttpSession session = request.getSession(false);
		Member mem = (Member) session.getAttribute("member");
		if(mem==null) {
			System.out.println("컨트롤러 | getCommentRootAll() 로그인 페이지로 이동");
			return "redirect:/member/login";
		}
		
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
		
		//처리한 데이터를 모델에 저장		
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

		//세션을 연결하여 안에서 Member DTO를 가져온다.
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
		
		//isGood_btn의 값에 따라 버튼의 색상인 goodColor의 값을 설정
		String goodColor = "";
		if(isGood_btn) {
			goodColor = "#FA5858";
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
		
		//isGood_btn의 값에 따라 버튼의 색상인 badColor의 값을 설정
		String badColor = "";
		if(isBad_btn) {
			badColor = "#58ACFA";
		}else {
			badColor = "white";
		}
		//mem_id를 통해 mem_nickName을 구하고 QnA DTO의 변수에 설정한다.
		System.out.println("컨트롤러 | 작성자의 nickName을 구하기 위해 memberService로 이동");
		if(qna!=null) {
			Member member = memberService.getMyInfo(qna.getMem_id());
			qna.setMem_nickName(member.getMem_nickName());
		}
		
		//question의 정보를 가지고 이동 URL을 문자열의 형태로 가공한다.
		Question question = questionService.getQuestionBySerial(qna.getQuestion_serial());
		String question_id = question.getQuestion_id();
		String question_url = "Q_read"+question_id+"/"+qna.getQuestion_serial();
		System.out.println(question_url);
		
		
		//처리한 데이터를 모델에 넣는다.
		model.addAttribute("qna", qna);
		model.addAttribute("isGood_btn", !isGood_btn);	//View에서 함수의 파라미터로 사용하기 때문에 !을 사용
		model.addAttribute("goodColor", goodColor);
		model.addAttribute("isBad_btn", !isBad_btn);	//View에서 함수의 파라미터로 사용하기 때문에 !을 사용
		model.addAttribute("badColor", badColor);
		model.addAttribute("question_url", question_url);
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
		String parent_mem_id = member.getMem_id();
		
		map.put("mem_id", parent_mem_id);
		map = qnaService.addCommentParent(map);		
		
		//root_mem_id : 글쓴이 | parent_mem_id : 댓글 작성자
		//글쓴이에게 댓글을 달았을 때 알림을 보내는 함수
		if((Boolean)map.get("success")) {
			String root_mem_id = (String) map.get("root_mem_id");
			int root = (int) map.get("comment_root");
			System.out.println("컨트롤러 | addCommentParent() root_mem_id: "+root_mem_id+" | root: "+root);
			memberService.mem_alarm_add(root_mem_id, parent_mem_id, root);
		}
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
		//map에서 comment_root의 데이터를 꺼내 파라미터로 사용하여 
		//DB에서 comment_root에 해당하는 QnA DTO를 ArrayList에 담고 HashMap에 담은 후에 가져온다.
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
		String child_mem_id = member.getMem_id();
		
		map.put("mem_id", child_mem_id);
		map = qnaService.addCommentChild(map);
		//parent_mem_id : 글쓴이 | child_mem_id : 대댓글 작성자
		//글쓴이에게 댓글을 달았을 때 알림을 보내는 함수
		if((Boolean)map.get("success")) {
			String parent_mem_id = (String) map.get("parent_mem_id");
			int root = (int) map.get("comment_root");
			System.out.println("컨트롤러 | addCommentChild() parent_mem_id: "+parent_mem_id+" | root: "+root);
			memberService.mem_alarm_add(parent_mem_id, child_mem_id, root);
		}
		return map;
	}

	//ajax로 parent를 지우기(수정) 위한 데이터를 받고 DB로 이동하는 함수
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
	
	//ajax로 child를 지우기(수정) 위한 데이터를 받고 DB로 이동하는 함수
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
