package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("errorPage")
public class ErrorController {
	
	@RequestMapping
	public String error() {
		System.out.println("===========에러 발생============");
		return "errorPage";		
	}
}
