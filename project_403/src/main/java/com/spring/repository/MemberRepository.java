package com.spring.repository;

import com.spring.domain.Member;

public interface MemberRepository {
	Member getMyInfo(String mem_id);
	void addMember(Member member);
	Member member_login(Member member);
	void member_update(Member member);
	void member_delete(Member member);
}
