package com.spring.controller;

import java.util.HashMap;

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
	
	//회원가입
	@GetMapping("/add")
	public String add(@ModelAttribute Member member,Model model)
	{	
		model.addAttribute("member",member);
		return "add";
	}
	@ResponseBody
	@PostMapping("/check")
	public String check_mem(@RequestBody HashMap<String,Object> map) {
		System.out.println(map.get("mem_id"));
		Member member=memberservice.getMyPage((String)map.get("mem_id"));
		if(member==null) {
			return "사용 가능한 아이디입니다.";
		}else {
			return "중복된 아이디입니다.";
		}
			
	}
	
	
	@PostMapping("/new")
	public String addMember(@ModelAttribute("member") Member member,Model model) {
	
		memberservice.addMember(member);
		model.addAttribute(member);
		return "My_page";
	}
	
	
	//회원 정보 확인(Read one)
	@PostMapping("/member")
	public String My_page(@RequestParam String mem_id,Model model) {
		System.out.println(mem_id+"받아온 멤버 아이디");
		Member member=memberservice.getMyPage(mem_id);
		System.out.println(member.getMem_id());
		model.addAttribute("member",member);
		return "My_page";
	}
	
	
	
	
}
