package com.spring.service;

import com.spring.domain.Member;

public interface MemberService {
	Member getMyPage(String mem_id);
	void addMember(Member member);
}
