package com.spring.repository;

public interface Favorite_Repository {
	void goodEnable(String mem_id, int qnaNum);
	void goodDisable(String mem_id, int qnaNum);
}
