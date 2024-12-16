package com.spring.domain;

import java.util.Date;

public class QnA {
	private int comment_num;			//게시번호(A.I)
	private String mem_id;				//회원 아이디
	private String question_serial;		//문제 고유 번호
	private String comment_title;		//질문 내용
	private Date comment_date;			//작성날짜
	private int comment_hit;			//조회수
	private int comment_good;			//추천수
	
	//Getter() Setter()
	public int getComment_num() {
		return comment_num;
	}
	public void setComment_num(int comment_num) {
		this.comment_num = comment_num;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getQuestion_serial() {
		return question_serial;
	}
	public void setQuestion_serial(String question_serial) {
		this.question_serial = question_serial;
	}
	public String getComment_title() {
		return comment_title;
	}
	public void setComment_title(String comment_title) {
		this.comment_title = comment_title;
	}
	public Date getComment_date() {
		return comment_date;
	}
	public void setComment_date(Date comment_date) {
		this.comment_date = comment_date;
	}
	public int getComment_hit() {
		return comment_hit;
	}
	public void setComment_hit(int comment_hit) {
		this.comment_hit = comment_hit;
	}
	public int getComment_good() {
		return comment_good;
	}
	public void setComment_good(int comment_good) {
		this.comment_good = comment_good;
	}
	
	
}
