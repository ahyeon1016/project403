package com.spring.domain;

public class Test 
{

	private String test_num; // 문제번호
	private String mem_id; // 유저 아이디
	private String test_name; // 시험지명
	private String test_time; // 걸린 시간
	private String test_date; // 문제푼날짜
	
	// getter + setter
	public String getTest_num() {
		return test_num;
	}
	public void setTest_num(String test_num) {
		this.test_num = test_num;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getTest_time() {
		return test_time;
	}
	public void setTest_time(String test_time) {
		this.test_time = test_time;
	}
	public String getTest_date() {
		return test_date;
	}
	public void setTest_date(String test_date) {
		this.test_date = test_date;
	}
	public String getTest_name() {
		return test_name;
	}
	public void setTest_name(String test_name) {
		this.test_name = test_name;
	}
	
	
}
