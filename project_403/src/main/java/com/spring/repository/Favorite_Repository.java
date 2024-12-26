package com.spring.repository;

public interface Favorite_Repository {
	void goodEnable(String mem_id, int qnaNum);
	void goodDisable(String mem_id, int qnaNum);
	int getTotalGood(int qnaNum);
	Boolean isGoodClicked(String user_id, int qnaNum);
}
