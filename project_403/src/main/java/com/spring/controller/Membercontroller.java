package com.spring.controller;

import java.net.URLDecoder;
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
import com.spring.repository.MemberRepository;
import com.spring.service.MemberService;

@Controller
@RequestMapping
public class Membercontroller {
	
	@Autowired
	MemberService memberservice;
	
	//home
	@GetMapping
	public String home(){
		return "member_home";
	}
	
	//회원가입 페이지로 이동
	@GetMapping("/add")
	public String add(@ModelAttribute Member member,Model model)
	{	
		model.addAttribute("member",member);
		return "member_add";
	}
	//아이디 중복검사
	@ResponseBody
	@PostMapping("/check")
	public HashMap<String,Object> check_mem(@RequestBody HashMap<String,Object> map) {
		HashMap<String,Object> returnmap=new HashMap<String,Object>();
		String value=null;
		Boolean isavail=false;
		System.out.println(map.get("mem_id"));
		Member member=memberservice.getMyPage((String)map.get("mem_id"));
		if(member==null) {
			 value="사용 가능한 아이디입니다.";
			 isavail=true;
		}else {
			 value="중복된 아이디입니다.";
			 isavail=false;
		}
		returnmap.put("key", value);
		returnmap.put("isavail", isavail);
		return returnmap;	
	}
	//회원가입을 누를 시 중복 검사
		@ResponseBody
		@PostMapping("/finalck")
		public HashMap<String,Object> final_check(@RequestBody HashMap<String,Object> map) {
			HashMap<String,Object> returnmap=new HashMap<String,Object>();
			String value=null;
			Boolean isavail=false;
			System.out.println(map.get("mem_id"));
			Member member2=memberservice.getMyPage((String)map.get("mem_id"));
			if(member2==null) {
				 value="회원가입 성공!";
				 isavail=true;
				 Member member=new Member();
				 member.setMem_id((String)map.get("mem_id"));
				 member.setMem_pw((String)map.get("mem_pw"));
				 member.setMem_nickName((String)map.get("mem_nick"));
				 memberservice.addMember(member);
			}else {
				 value="중복된 아이디입니다.";
				 isavail=false;
			}
			returnmap.put("key", value);
			returnmap.put("isavail", isavail);
			return returnmap;	
		}
	
	//회원가입 C
	@PostMapping("/new")
	public String addMember(@ModelAttribute("member") Member member,Model model) {
		memberservice.addMember(member);
		model.addAttribute(member);
		return "member_home";
	}
	//로그인
	@GetMapping("/login")
	public String Login_page(@ModelAttribute("member") Member member,Model model) {
		model.addAttribute("member",member);				
		return "member_login";
	}
	//세션에 멤버 정보 담으며 로그인하기.
	@PostMapping("/login")
	public String login(@ModelAttribute("member") Member member,Model model,HttpServletRequest req) {
		HttpSession session=req.getSession();
		member=memberservice.member_login(member);
		if(member!=null) {
			System.out.println("logiiiiiiiiiiiiiiiiiiiiin"+member.getMem_nickName());
			session.setAttribute("member", member);
			return "member_My_page";
		}
		else {
			return "member_login_fail";
		}
		
	}
	//회원 정보 확인(Read one)
	@GetMapping("/member")
	public String mem_info(@RequestParam String mem_id,Model model) {
		System.out.println(mem_id+"받아온 멤버 아이디");
		Member member=memberservice.getMyPage(mem_id);
		System.out.println(member.getMem_id());
		model.addAttribute("member",member);
		return "member_who";
	}
	//마이페이지로 이동
	@GetMapping("/mypage")
	public String My_page() {
		return "member_My_page";
	}
	//로그아웃
	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "member_home";
	}
	//정보 수정
}
