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
	public Member social_login(Member member) {
		return memberRepository.social_login(member);
	}


	@Override
	public boolean naver_info(Member member) {
		return memberRepository.naver_info(member);
	}


	@Override
	public boolean kakao_info(Member member) {
		return memberRepository.kakao_info(member);
	}


	@Override
	public Member getMyInfo(String mem_id) {
		System.out.println("서비스접근");
		return memberRepository.getMyInfo(mem_id);
	}


	@Override
	public void addMember(Member member) {
		memberRepository.addMember(member);
	}
	@Override
	public Member member_login(Member member) {
		return memberRepository.member_login(member);
	}


	@Override
	public void member_update(Member member) {
		memberRepository.member_update(member);
	}


	@Override
	public void member_delete(Member member) {
		memberRepository.member_delete(member);
	}
	
}
