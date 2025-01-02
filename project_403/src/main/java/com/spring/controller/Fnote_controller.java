package com.spring.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.domain.Fnote;
import com.spring.domain.Member;
import com.spring.domain.Question;
import com.spring.domain.Test;
import com.spring.service.FnoteService;
import com.spring.service.MemberService;
import com.spring.service.Question_Service;
import com.spring.service.TestService;

@Controller
@RequestMapping("/fnote")
public class Fnote_controller {

	@Autowired 
	MemberService memberservice;
	
	@Autowired
	FnoteService fnoteservice;
	
	@Autowired
	TestService testservice;
	
	@Autowired
	Question_Service questionservice;
	
	//자신의 모든 노트를 조회하는 페이지로 이동
	@PostMapping("notelist")
	public String note_list(HttpServletRequest req,Model model) {
		ArrayList<Fnote> note_list=new ArrayList<Fnote>();
		System.out.println("노트리스트 페이지에 접속을 완료했다.");
		HttpSession session=req.getSession(false);
		Member member=(Member)session.getAttribute("member");
		String mem_id=member.getMem_id();
		note_list=fnoteservice.note_mine(mem_id);
		ArrayList<Test> test_list=new ArrayList<Test>();
		for(int i=0;i<note_list.size();i++) {
			test_list.add(testservice.getTestByNum(note_list.get(i).getTest_num()));
		}
		model.addAttribute("testlist",test_list);
		return "fnote_list";
	}
	
	@PostMapping("mynote")
	public String my_note(@RequestParam int testnum,Model model,HttpServletRequest req) {
		//테스트 조회해서 문제들 다 보여주기.
		HttpSession session=req.getSession(false);
		Member member=(Member) session.getAttribute("member");
		System.out.println("내 노트를 내놩라ㅏ라라라라라라라라라라ㅏ라"+testnum);
		Test test=testservice.getTestByNum(testnum);
		System.out.println("시리얼을 내놓거라"+test.getSerial()[0]);
		String[] questions=test.getSerial();
		ArrayList<Question> question_list=new ArrayList<Question>();
		for(int i=0;i<questions.length;i++) {
			question_list.add(questionservice.getQuestionBySerial(questions[i]));
		}
		for(int i=0;i<question_list.size();i++) {
			if(question_list.get(i).getQuestion_id().equals("MCQ")) {
				String ans=question_list.get(i).getQuestion_ans();
				char last_index_char=ans.charAt(ans.length()-1);
				int last_index_int=last_index_char-'0';
				String[] answers=ans.split("\\|★\\|");
				System.out.println(answers[last_index_int-1]);
				question_list.get(i).setQuestion_ans(last_index_int+"."+answers[last_index_int-1]);
			}
			else if(question_list.get(i).getQuestion_id().equals("CP")) {
				String content=question_list.get(i).getQuestion_content();
				String[] content_split=content.split("\\|★\\|");
				question_list.get(i).setQuestion_content(content_split[0]);
			}
			
		}
		Fnote fnote=fnoteservice.note_by_testnum(testnum);
		model.addAttribute("questionlist",question_list);
		session.setAttribute("fnote",fnote);
		return "fnote_my_note";
	}
	@PostMapping("update")
	@ResponseBody
	public void note_update(@RequestBody HashMap<String,Object> map,HttpServletRequest req){
		String notes=(String)map.get("notes");
		System.out.println(notes);
		String[] note_split=notes.split(",");
		System.out.println(note_split[0]);
		HttpSession session=req.getSession(false);
		Member member=(Member) session.getAttribute("member");
		Fnote fnote=(Fnote)session.getAttribute("fnote");
		System.out.println(fnote.getTest_num());
		System.out.println(member.getMem_id());
		fnoteservice.note_update(member.getMem_id(),fnote.getTest_num(), notes);
	}
	@PostMapping("delete")
	public String note_delete(HttpServletRequest req) {
		HttpSession session=req.getSession(false);
		Fnote fnote=(Fnote)session.getAttribute("fnote");
		System.out.println("없애는거~~~"+fnote.getMem_id()+fnote.getTest_num());
		fnoteservice.note_delete(fnote.getMem_id(), fnote.getTest_num());
		return "member_My_page";
	}
}
