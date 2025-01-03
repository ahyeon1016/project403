package com.spring.domain;

public class Test 
{
	private int test_num; // 시험지 번호
	private String mem_id; // 시험 작성자 ID
	private String test_name; // 시험지 제목
	private String test_pw; // 시험지 비밀번호
	private String test_openYN; // 시험지 공개/비공개
	private String sub_name; // 과목명
	private String sub_chap; // 챕터명
	private int test_hit; // 조회수
	private String[] serial; // 해당 시험지 문제
	private boolean visible; // 삭제시 감춤
	private String updateBtn; // 전체글 조회시 본인이 작성한 글인지 여부확인
	private String mem_nickName; // 시험 작성자 닉네임
	
	// 기본 생성자
	public Test() 
	{
		
	}
	
	// getter + setter
	public int getTest_num() {
		return test_num;
	}

	public void setTest_num(int test_num) {
		this.test_num = test_num;
	}

	public String getMem_id() {
		return mem_id;
	}

	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}

	public String getTest_name() {
		return test_name;
	}

	public void setTest_name(String test_name) {
		this.test_name = test_name;
	}

	public String getTest_pw() {
		return test_pw;
	}

	public void setTest_pw(String test_pw) {
		this.test_pw = test_pw;
	}

	public String getTest_openYN() {
		return test_openYN;
	}

	public void setTest_openYN(String test_openYN) {
		this.test_openYN = test_openYN;
	}

	public String getSub_name() {
		return sub_name;
	}

	public void setSub_name(String sub_name) {
		this.sub_name = sub_name;
	}

	public int getTest_hit() {
		return test_hit;
	}

	public void setTest_hit(int test_hit) {
		this.test_hit = test_hit;
	}

	public String getSub_chap() {
		return sub_chap;
	}
	public void setSub_chap(String sub_chap) {
		this.sub_chap = sub_chap;
	}

	public String[] getSerial() {
		return serial;
	}

	public void setSerial(String[] serial) {
		this.serial = serial;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public String getUpdateBtn() {
		return updateBtn;
	}

	public void setUpdateBtn(String updateBtn) {
		this.updateBtn = updateBtn;
	}

	public String getMem_nickName() {
		return mem_nickName;
	}

	public void setMem_nickName(String mem_nickName) {
		this.mem_nickName = mem_nickName;
	}
	
	
}
