package com.spring.controller;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("favorite")
public class Favorite_Controller {
	
	@ResponseBody
	@PostMapping("/good")
	public HashMap<String, Object> favoriteGood(
			@RequestBody HashMap<String, Object> map){
		System.out.println("==========================================");
		System.out.println("컨트롤러 | favoriteGood() 도착");
		Boolean isClicked = (Boolean)map.get("isClicked");
		
		//isClicked 값 변환
		if(isClicked == true) {
			isClicked = false;
		} else if(isClicked == false){
			isClicked = true;
		}
		
		System.out.println(isClicked);
		return null;
	}
}
