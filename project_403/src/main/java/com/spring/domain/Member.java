package com.spring.domain;

public class Member {
	private String mem_id;//멤버 아이디
	private String mem_pw;//멤버 비밀번호
	private String mem_nickName;//멤버 닉네임
	private int mem_point;//멤버 등급 포인트
	private int mem_exp;//멤버 레벨
	private boolean mem_admin;//관리자 권한
	private String mem_email;//멤버 이메일
	private boolean mem_confirmed;//인증 여부
	private String mem_serial;//멤버 인증코드
	
	
	public String getMem_email() {
		return mem_email;
	}
	public void setMem_email(String mem_email) {
		this.mem_email = mem_email;
	}
	public boolean isMem_confirmed() {
		return mem_confirmed;
	}
	public void setMem_confirmed(boolean mem_confirmed) {
		this.mem_confirmed = mem_confirmed;
	}
	public String getMem_serial() {
		return mem_serial;
	}
	public void setMem_serial(String mem_serial) {
		this.mem_serial = mem_serial;
	}
	public boolean isMem_admin() {
		return mem_admin;
	}
	public void setMem_admin(boolean mem_admin) {
		this.mem_admin = mem_admin;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getMem_pw() {
		return mem_pw;
	}
	public void setMem_pw(String mem_pw) {
		this.mem_pw = mem_pw;
	}
	public String getMem_nickName() {
		return mem_nickName;
	}
	public void setMem_nickName(String mem_nickName) {
		this.mem_nickName = mem_nickName;
	}
	public int getMem_point() {
		return mem_point;
	}
	public void setMem_point(int mem_point) {
		this.mem_point = mem_point;
	}
	public int getMem_exp() {
		return mem_exp;
	}
	public void setMem_exp(int mem_exp) {
		this.mem_exp = mem_exp;
	}
	
	
}
