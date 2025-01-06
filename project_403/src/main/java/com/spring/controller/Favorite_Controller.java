package com.spring.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.domain.Member;
import com.spring.service.Favorite_Service;

@Controller
@RequestMapping("favorite")
public class Favorite_Controller {
	
	@Autowired
	Favorite_Service favoriteService;
	
	//ajax로 View에서 좋아요를 활성화 했는지 비활성화 했는지 
	//여부를 판단하기 위한 Boolean 값을 받아 DB로 이동한다.
	@ResponseBody
	@PostMapping("/good")
	public HashMap<String, Object> favoriteGood(
			@RequestBody HashMap<String, Object> map,
			HttpServletRequest request){
		System.out.println("==========================================");
		System.out.println("컨트롤러 | favoriteGood() 도착");
		//Session에서 Member DTO를 가져와 mem_id의 값을얻는다.
		HttpSession session = request.getSession(false);
		Member member = (Member)session.getAttribute("member");
		String mem_id = member.getMem_id();
		
		//map에 저장된 Boolean값과 qnaNum값을 꺼내 변수에 담고 Boolean값을 활용해 
		//조건문으로 좋아요 활성화 비활성화 여부를 판단하여 DB로 이동한다.
		Boolean isClicked = (Boolean)map.get("isClicked");
		int qnaNum = (int)map.get("qnaNum");
		
		if(isClicked) {
			System.out.println("컨트롤러 | 좋아요 활성화 : "+isClicked);
			favoriteService.goodEnable(mem_id, qnaNum);
		}else {
			System.out.println("컨트롤러 | 좋아요 비활성화 : "+isClicked);
			favoriteService.goodDisable(mem_id, qnaNum);
		}
		
		map.put("isClicked", isClicked);
		return map;
	}
	
	//ajax로 View에서 싫어요를 활성화 했는지 비활성화 했는지 
	//여부를 판단하기 위한 Boolean 값을 받아 DB로 이동한다.
	@ResponseBody
	@PostMapping("/bad")
	public HashMap<String, Object> favoriteBad(
			@RequestBody HashMap<String, Object> map,
			HttpServletRequest request){
		System.out.println("==========================================");
		System.out.println("컨트롤러 | favoriteBad() 도착");
		//Session에서 Member DTO를 가져와 mem_id의 값을얻는다.
		HttpSession session = request.getSession(false);
		Member member = (Member)session.getAttribute("member");
		String mem_id = member.getMem_id();
		
		//map에 저장된 Boolean값과 qnaNum값을 꺼내 변수에 담고 Boolean값을 활용해 
		//조건문으로 싫어요 활성화 비활성화 여부를 판단하여 DB로 이동한다.
		Boolean isClicked = (Boolean)map.get("isClicked");
		int qnaNum = (int)map.get("qnaNum");
		
		if(isClicked) {
			System.out.println("컨트롤러 | 싫어요 활성화 : "+isClicked);
			favoriteService.badEnable(mem_id, qnaNum);
		}else {
			System.out.println("컨트롤러 | 싫어요 비활성화 : "+isClicked);
			favoriteService.badDisable(mem_id, qnaNum);
		}
		
		map.put("isClicked", isClicked);
		return map;
	}
}
