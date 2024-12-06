package com.spring.domain;

import org.springframework.web.multipart.MultipartFile;

public class Question {
	private int question_num;			//문제번호(A_I)
	private String question_content;	//문제 내용
	private String question_ans;		//정답
	private MultipartFile question_img;	//문제 이미지
	private String question_img_name;	//문제 이미지 이름
	private int question_plus;			//문제풀이 추가 카운트
	private int question_count;			//문제풀이 횟수
	private String sub_code_sum;		//과목코드(과목코드+챕터코드)
	private int mem_serial;				//멤버식별변수
	private int question_serial;		//문제고유번호(과목코드+문제번호)
	
	//Getter(), Setter()
	public int getQuestion_num() {
		return question_num;
	}
	public void setQuestion_num(int question_num) {
		this.question_num = question_num;
	}
	public String getQuestion_content() {
		return question_content;
	}
	public void setQuestion_content(String question_content) {
		this.question_content = question_content;
	}
	public String getQuestion_ans() {
		return question_ans;
	}
	public void setQuestion_ans(String question_ans) {
		this.question_ans = question_ans;
	}
	public MultipartFile getQuestion_img() {
		return question_img;
	}
	public void setQuestion_img(MultipartFile question_img) {
		this.question_img = question_img;
	}
	public String getQuestion_img_name() {
		return question_img_name;
	}
	public void setQuestion_img_name(String question_img_name) {
		this.question_img_name = question_img_name;
	}
	public int getQuestion_plus() {
		return question_plus;
	}
	public void setQuestion_plus(int question_plus) {
		this.question_plus = question_plus;
	}
	public int getQuestion_count() {
		return question_count;
	}
	public void setQuestion_count(int question_count) {
		this.question_count = question_count;
	}
	public String getSub_code_sum() {
		return sub_code_sum;
	}
	public void setSub_code_sum(String sub_code_sum) {
		this.sub_code_sum = sub_code_sum;
	}
	public int getMem_serial() {
		return mem_serial;
	}
	public void setMem_serial(int mem_serial) {
		this.mem_serial = mem_serial;
	}
	public int getQuestion_serial() {
		return question_serial;
	}
	public void setQuestion_serial(int question_serial) {
		this.question_serial = question_serial;
	}
}
