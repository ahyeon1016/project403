package com.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.domain.Member;
import com.spring.repository.MemberRepository;
@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	MemberRepository memberRepository;
	
	
	@Override
	public Member getMyPage(String mem_id) {
		System.out.println("서비스접근");
		return memberRepository.getMyPage(mem_id);
	}


	@Override
	public void addMember(Member member) {
		memberRepository.addMember(member);
	}
	
}
