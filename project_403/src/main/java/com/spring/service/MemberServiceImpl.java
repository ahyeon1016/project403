package com.spring.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.domain.Member;
import com.spring.repository.MemberRepository;
@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	MemberRepository memberRepository;
	
	
	@Override
	public void mem_alarm_update(Member member) {
		memberRepository.mem_alarm_update(member);
	}





	@Override
	public void mem_alarm_add(String mem_id, String comment_id) {
		memberRepository.mem_alarm_add(mem_id,comment_id);
		
	}





	@Override
	public void mem_nickname_change(Member member) {
		memberRepository.mem_nickname_change(member);
	}





	@Override
	public void mem_confirm(int mem_serial) {
		memberRepository.mem_confirm(mem_serial);
	}





	@Override
	public int mem_num() {
		return memberRepository.mem_num();
	}


	


	@Override
	public int mem_serial(String user_mail,String user_id) {
		return memberRepository.mem_serial(user_mail,user_id);
	}





	@Override
	public ArrayList read_all_Member(int limit_num) {
		return memberRepository.read_all_Member(limit_num);
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


	@Override
	public String getNickNameBySerial(int serial) {
		return memberRepository.getNickNameBySerial(serial);
	}
	
}
