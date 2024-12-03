package com.spring.domain;

public class Subject {
	private int sub_code;	//문제코드
	private String sub_name;	//과목이름
	private String sub_chap;	//과목챕터
	private int sub_num;	//과정 번호(AUTO_INCREMENT)
	private String sub_serial;	//고유 번호
	
	//Getter(), Setter()
	public int getSub_code() {
		return sub_code;
	}
	public void setSub_code(int sub_code) {
		this.sub_code = sub_code;
	}
	public String getSub_name() {
		return sub_name;
	}
	public void setSub_name(String sub_name) {
		this.sub_name = sub_name;
	}
	public String getSub_chap() {
		return sub_chap;
	}
	public void setSub_chap(String sub_chap) {
		this.sub_chap = sub_chap;
	}
	public int getSub_num() {
		return sub_num;
	}
	public void setSub_num(int sub_num) {
		this.sub_num = sub_num;
	}
	public String getSub_serial() {
		return sub_serial;
	}
	public void setSub_serial(String sub_serial) {
		this.sub_serial = sub_serial;
	}
	
	
}
