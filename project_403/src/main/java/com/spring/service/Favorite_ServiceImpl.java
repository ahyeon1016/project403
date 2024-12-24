package com.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.repository.Favorite_Repository;

@Service
public class Favorite_ServiceImpl implements Favorite_Service{

	@Autowired
	Favorite_Repository favoriteRepository;
	
	@Override
	public void goodEnable(String mem_id, int qnaNum) {
		System.out.println("서비스 | goodEnable() 도착");
		favoriteRepository.goodEnable(mem_id, qnaNum);
	}

	@Override
	public void goodDisable(String mem_id, int qnaNum) {
		System.out.println("서비스 | goodDisable() 도착");
		favoriteRepository.goodDisable(mem_id, qnaNum);
	}

	@Override
	public int getTotalGood(int qnaNum) {
		System.out.println("서비스 | getTotalGood() 도착");
		return favoriteRepository.getTotalGood(qnaNum);
	}

}
