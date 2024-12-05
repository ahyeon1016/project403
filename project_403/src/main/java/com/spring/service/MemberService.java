package com.spring.service;

import com.spring.domain.Member;

public interface MemberService {
	Member getMyInfo(String mem_id);
	void addMember(Member member);
	Member member_login(Member member);
	void member_update(Member member);
}
