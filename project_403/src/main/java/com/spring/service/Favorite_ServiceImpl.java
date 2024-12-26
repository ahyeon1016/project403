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

	@Override
	public Boolean isGoodClicked(String user_id, int qnaNum) {
		System.out.println("서비스 | getTotalGood() 도착");
		return favoriteRepository.isGoodClicked(user_id, qnaNum);
	}

	@Override
	public void badEnable(String mem_id, int qnaNum) {
		System.out.println("서비스 | badEnable() 도착");
		favoriteRepository.badEnable(mem_id, qnaNum);
	}

	@Override
	public void badDisable(String mem_id, int qnaNum) {
		System.out.println("서비스 | badDisable() 도착");
		favoriteRepository.badDisable(mem_id, qnaNum);
	}

	@Override
	public int getTotalBad(int qnaNum) {
		System.out.println("서비스 | getTotalBad() 도착");
		return favoriteRepository.getTotalBad(qnaNum);
	}

	@Override
	public Boolean isBadClicked(String user_id, int qnaNum) {
		System.out.println("서비스 | isBadClicked() 도착");
		return favoriteRepository.isBadClicked(user_id, qnaNum);
	}

}
