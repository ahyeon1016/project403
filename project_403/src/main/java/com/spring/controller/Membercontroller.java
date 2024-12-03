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
		return "home";
	}
	
	//회원가입 페이지로 이동
	@GetMapping("/add")
	public String add(@ModelAttribute Member member,Model model)
	{	
		model.addAttribute("member",member);
		return "add";
	}
	//아이디 중복검사
	@ResponseBody
	@PostMapping("/check")
	public String check_mem(@RequestBody HashMap<String,Object> map) {
		System.out.println(map.get("mem_id"));
		Member member=memberservice.getMyPage((String)map.get("mem_id"));
		if(member==null) {
			String a="사용 가능한 아이디입니다.";
			return a;
		}else {
			String a="중복된 아이디입니다.";
			return a;
		}
			
	}
	
	//회원가입 C
	@PostMapping("/new")
	public String addMember(@ModelAttribute("member") Member member,Model model) {
		Member member2=memberservice.member_login(member);
		if(member.getMem_id()==member2.getMem_id()) {
			
		}
		memberservice.addMember(member);
		model.addAttribute(member);
		return "My_page";
	}
	//로그인
	@GetMapping("/login")
	public String Login_page(@ModelAttribute("member") Member member,Model model) {
		model.addAttribute("member",member);				
		return "login";
	}
	//세션에 멤버 정보 담으며 로그인하기.
	@PostMapping("/login")
	public String login(@ModelAttribute("member") Member member,Model model,HttpServletRequest req) {
		HttpSession session=req.getSession();
		member=memberservice.member_login(member);
		if(member!=null) {
			System.out.println("logiiiiiiiiiiiiiiiiiiiiin"+member.getMem_nickName());
			session.setAttribute("member", member);
			return "My_page";
		}
		else {
			return "login_fail";
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
		return "My_page";
	}
	//로그아웃
	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "home";
	}
	
}
