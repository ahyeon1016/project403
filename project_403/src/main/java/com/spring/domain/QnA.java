package com.spring.domain;

import java.util.Date;

public class QnA {
	private int comment_num;			//게시번호(A.I)
	private String mem_id;				//회원 아이디
	private String question_serial;		//문제 고유 번호
	private int comment_root;			//질문글 갯수
	private int comment_parent;			//댓글(null이면 질문글)
	private int comment_child;			//대댓글
	private String comment_title;		//질문 제목
	private String comment_content;		//질문 내용
	private Date comment_date;			//작성날짜
	private int comment_hit;			//조회수
	private int comment_totalGood;		//좋아요 갯수(DB X)
	private int comment_totalBad;		//좋아요 갯수(DB X)
	private String mem_nickName;		//회원 닉네임(DB X)

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
	public int getComment_root() {
		return comment_root;
	}
	public void setComment_root(int comment_root) {
		this.comment_root = comment_root;
	}
	public int getComment_parent() {
		return comment_parent;
	}
	public void setComment_parent(int comment_parent) {
		this.comment_parent = comment_parent;
	}
	public int getComment_child() {
		return comment_child;
	}
	public void setComment_child(int comment_child) {
		this.comment_child = comment_child;
	}
	public String getComment_title() {
		return comment_title;
	}
	public void setComment_title(String comment_title) {
		this.comment_title = comment_title;
	}
	public String getComment_content() {
		return comment_content;
	}
	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
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
	public String getMem_nickName() {
		return mem_nickName;
	}
	public void setMem_nickName(String mem_nickName) {
		this.mem_nickName = mem_nickName;
	}
	public int getComment_totalGood() {
		return comment_totalGood;
	}
	public void setComment_totalGood(int comment_totalGood) {
		this.comment_totalGood = comment_totalGood;
	}
	public int getComment_totalBad() {
		return comment_totalBad;
	}
	public void setComment_totalBad(int comment_totalBad) {
		this.comment_totalBad = comment_totalBad;
	}

}
