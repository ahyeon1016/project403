package com.spring.domain;

import org.springframework.web.multipart.MultipartFile;

public class Question {
	private int question_num;	//문제번호
	private String question_inner;	//문제 내용
	private String question_ans;	//정답
	private MultipartFile question_img;	//문제 이미지
	private String question_img_name;	//문제 이미지 이름
	private int question_plus;	//문제풀이 추가 카운트
	private int question_count;	//문제풀이 횟수
	private int sub_code;	//문제코드
	
	//Getter(), Setter()
	public int getQuestion_num() {
		return question_num;
	}
	public void setQuestion_num(int question_num) {
		this.question_num = question_num;
	}
	public String getQuestion_inner() {
		return question_inner;
	}
	public void setQuestion_inner(String question_inner) {
		this.question_inner = question_inner;
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
	public int getSub_code() {
		return sub_code;
	}
	public void setSub_code(int sub_code) {
		this.sub_code = sub_code;
	}	
	
}
