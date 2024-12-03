package com.spring.repository;

import com.spring.domain.Member;

public interface MemberRepository {
	Member getMyPage(String mem_id);
	void addMember(Member member);
	Member member_login(Member member);

}
