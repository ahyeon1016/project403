package com.spring.domain;

public class Favorite {
	private int comment_num;
	private String mem_id;
	private Boolean comment_good;
	private Boolean comment_bad;
	
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
	public Boolean getComment_good() {
		return comment_good;
	}
	public void setComment_good(Boolean comment_good) {
		this.comment_good = comment_good;
	}
	public Boolean getComment_bad() {
		return comment_bad;
	}
	public void setComment_bad(Boolean comment_bad) {
		this.comment_bad = comment_bad;
	}
	
}
