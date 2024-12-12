package com.spring.service;

import java.util.ArrayList;

import com.spring.domain.Member;

public interface MemberService {
	Member getMyInfo(String mem_id);
	void addMember(Member member);
	Member member_login(Member member);
	void member_update(Member member);
	void member_delete(Member member);
	boolean naver_info(Member member);
	boolean kakao_info(Member member);
	ArrayList<Member> read_all_Member(int limit_num);
	ArrayList<Member> search_admin(String search_data);
	int mem_num();
}
