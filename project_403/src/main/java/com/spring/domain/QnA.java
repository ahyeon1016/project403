package com.spring.domain;

import java.util.Date;

public class QnA {
	private int test_num;	//문제 번호
	private String comment_title;	//제목
	private String comment_inner;	//질문내용
	private Date comment_date;	//작성날짜
	private int comment_num;	//게시번호
	private int comment_hit;	//조회수
	private int comment_good;	//추천수
	
	//Getter(), Setter()
	public int getTest_num() {
		return test_num;
	}
	public void setTest_num(int test_num) {
		this.test_num = test_num;
	}
	public String getComment_title() {
		return comment_title;
	}
	public void setComment_title(String comment_title) {
		this.comment_title = comment_title;
	}
	public String getComment_inner() {
		return comment_inner;
	}
	public void setComment_inner(String comment_inner) {
		this.comment_inner = comment_inner;
	}
	public Date getComment_date() {
		return comment_date;
	}
	public void setComment_date(Date comment_date) {
		this.comment_date = comment_date;
	}
	public int getComment_num() {
		return comment_num;
	}
	public void setComment_num(int comment_num) {
		this.comment_num = comment_num;
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
