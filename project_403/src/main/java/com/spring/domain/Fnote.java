package com.spring.domain;

import java.util.ArrayList;

public class Fnote {
	private String[] note;//정리노트 내용
	private String mem_id;//멤버 아이디
	private ArrayList test_list;//문제 담는곳
	private int test_num;//시험 번호(식별번호) 
	
	public int getTest_num() {
		return test_num;
	}
	public void setTest_num(int test_num) {
		this.test_num = test_num;
	}
	public ArrayList getTest_list() {
		return test_list;
	}
	public void setTest_list(ArrayList test_list) {
		this.test_list = test_list;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String[] getNote() {
		return note;
	}
	public void setNote(String[] note) {
		this.note = note;
	}
	
	
}
