package com.spring.domain;

public class Subject {
	private int sub_num;	//과정 번호(AUTO_INCREMENT)
	private int sub_name_code;	//과목코드
	private int sub_chap_code;	//챕터코드
	private String sub_name;	//과목이름
	private String sub_chap;	//챕터이름
	
	//Getter(), Setter()
	public int getSub_num() {
		return sub_num;
	}
	public void setSub_num(int sub_num) {
		this.sub_num = sub_num;
	}
	public int getSub_name_code() {
		return sub_name_code;
	}
	public void setSub_name_code(int sub_name_code) {
		this.sub_name_code = sub_name_code;
	}
	public int getSub_chap_code() {
		return sub_chap_code;
	}
	public void setSub_chap_code(int sub_chap_code) {
		this.sub_chap_code = sub_chap_code;
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
}
