package com.spring.service;

import java.util.ArrayList;

import com.spring.domain.Member;

public interface MemberService {
	Member getMyInfo(String mem_id);//본인 정보 조회
	void addMember(Member member);//회원가입
	Member member_login(Member member);//회원 로그인시 정보 조회
	void member_update(Member member);//회원 정보 수정
	void member_delete(Member member);//회원 탈퇴
	boolean naver_info(Member member);//네이버에 회원 정보가 존재하는지 조회
	boolean kakao_info(Member member);//카카오에 회원 정보가 존재하는지 조회
	ArrayList<Member> read_all_Member(int limit_num);//전체 회원 조회
	int mem_num();//전체 회원의 숫자
	int mem_serial(String user_mail,String user_id);//이메일로 멤버 시리얼 조회
	void mem_confirm(int mem_serial);//시리얼로 멤버 인증
	void mem_nickname_change(Member member);//닉네임 바꾸기
	void mem_alarm_add(String mem_id,String comment_id);//알림기능
	void mem_alarm_update(Member member);//알림 삭제
	void member_lvup(int point,int exp,String mem_id);
}