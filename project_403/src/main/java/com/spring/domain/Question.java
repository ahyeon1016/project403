package com.spring.domain;

import org.springframework.web.multipart.MultipartFile;

public class Question {
	private int question_num;	//문제번호
	private String question_ans;	//정답
	private MultipartFile question_img;	//문제 이미지
	private String questuin_img_name;	//문제 이미지 이름
	private int question_plus;	//문제풀이 추가 카운트
	private int question_count;	//문제풀이 횟수
	private String question_chap;	//문제분류
	
	//Getter(), Setter()
	public int getQuestion_num() {
		return question_num;
	}
	public void setQuestion_num(int question_num) {
		this.question_num = question_num;
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
	public String getQuestuin_img_name() {
		return questuin_img_name;
	}
	public void setQuestuin_img_name(String questuin_img_name) {
		this.questuin_img_name = questuin_img_name;
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
	public String getQuestion_chap() {
		return question_chap;
	}
	public void setQuestion_chap(String question_chap) {
		this.question_chap = question_chap;
	}
	
	
	
}
