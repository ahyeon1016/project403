package com.spring.controller;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.regex.Pattern;

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
	@GetMapping("/")
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
		Member member=memberservice.getMyInfo((String)map.get("mem_id"));
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
			String email_pattern="^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
			String email=map.get("mem_email").toString();
			String pw=map.get("mem_pw").toString();
			int pw_length=pw.length();
			System.out.println(pw_length);
			if((pw_length<=15&&pw_length>=3)&&Pattern.matches(email_pattern, email)) {
			Member member2=memberservice.getMyInfo((String)map.get("mem_id"));
			if(member2==null) {
				 value="회원가입 성공!";
				 isavail=true;
				 Member member=new Member();
				 member.setMem_id((String)map.get("mem_id"));
				 member.setMem_pw((String)map.get("mem_pw"));
				 member.setMem_nickName((String)map.get("mem_nick"));
				 member.setMem_email((String)map.get("mem_email"));
				 memberservice.addMember(member);
			}else 
			{
				 value="중복된 아이디입니다.";
				 isavail=false;
			}
			}else 
				value="형식에 맞게 입력하세요.";
			returnmap.put("key", value);
			System.out.println(returnmap.get("key"));
			returnmap.put("isavail", isavail);
			return returnmap;	
		}
	
//	//회원가입 C
//	@PostMapping("/new")
//	public String addMember(@ModelAttribute("member") Member member,Model model) {
//		memberservice.addMember(member);
//		model.addAttribute(member);
//		return "member_home";
//	}
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
	@PostMapping("/member")
	public String mem_info(@RequestParam String mem_id,Model model) {
		System.out.println(mem_id+"받아온 멤버 아이디");
		Member member=memberservice.getMyInfo(mem_id);
		System.out.println(member.getMem_id());
		model.addAttribute("member",member);
		return "member_who";
	}
	//정보 수정 페이지 이동
	@PostMapping("update")
	public String update_member(@ModelAttribute("member") Member member,@RequestParam String mem_id,Model model) {
			member=memberservice.getMyInfo(mem_id);
			model.addAttribute(member);
			return "member_update";
	}
	//정보 수정 기능
	@PostMapping("update/sequence")
	public String update_sequence(@ModelAttribute("member")Member member,HttpSession session) {
		System.out.println(member.getMem_id());
		memberservice.member_update(member);
		session.invalidate();
		return "member_home";
	}
	//회원 탈퇴 페이지 이동
	@PostMapping("delete")
	public String byebye_member(@RequestParam String mem_id,Model model) {
		model.addAttribute("mem_id",mem_id);
		return "member_bye";
	}
	//회원 탈퇴 기능
	@PostMapping("delete_bye")
	public String bye(HttpSession session,HttpServletRequest req) {
		Member member=new Member();
		Member sesmember=(Member)session.getAttribute("member");
		String mem_id=sesmember.getMem_id();
		String mem_pw=req.getParameter("pw");
		member.setMem_id(mem_id);
		member.setMem_pw(mem_pw);
		memberservice.member_delete(member);
		session.invalidate();
		return "member_home";
	}
	
	
	
	//마이페이지로 이동
	@PostMapping("/mypage")
	public String My_page() {
		return "member_My_page";
	}
	//로그아웃
	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "member_home";
	}
	 
}
