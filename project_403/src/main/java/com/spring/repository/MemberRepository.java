package com.spring.repository;

import java.util.ArrayList;

import com.spring.domain.Member;

public interface MemberRepository {
	Member getMyInfo(String mem_id);
	void addMember(Member member);
	Member member_login(Member member);
	void member_update(Member member);
	void member_delete(Member member);
	boolean naver_info(Member member);
	boolean kakao_info(Member member);
	ArrayList<Member> read_all_Member(int limit_num);
	int mem_num();
	int mem_serial(String user_mail,String user_id);
	void mem_confirm(int mem_serial);
	void mem_nickname_change(Member member);
	void mem_alarm_add(String mem_id,String comment_id, int root);
	void mem_alarm_update(Member member);
	String getNickNameBySerial(int serial);
	void member_lvup(int point,int exp,String mem_id);
	void item_buy(int point,String mem_id);
}
