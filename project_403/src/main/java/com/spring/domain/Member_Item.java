package com.spring.domain;

public class Member_Item {
	
	private boolean mem_itemA;//멤버 아이템1
	private boolean mem_itemB;//멤버 아이템2
	private String mem_color;//멤버 닉네임컬러
	private String mem_id;//멤버 아이디
	
	
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public boolean isMem_itemA() {
		return mem_itemA;
	}
	public void setMem_itemA(boolean mem_itemA) {
		this.mem_itemA = mem_itemA;
	}
	public boolean isMem_itemB() {
		return mem_itemB;
	}
	public void setMem_itemB(boolean mem_itemB) {
		this.mem_itemB = mem_itemB;
	}
	public String getMem_color() {
		return mem_color;
	}
	public void setMem_color(String mem_color) {
		this.mem_color = mem_color;
	}
	
	
}
