package com.spring.service;

public interface Favorite_Service {
	void goodEnable(String mem_id, int qnaNum);
	void goodDisable(String mem_id, int qnaNum);
	int getTotalGood(int qnaNum);
}
