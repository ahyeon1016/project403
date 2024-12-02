package com.spring.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.domain.Test;
import com.spring.service.TestService;

@Controller
@RequestMapping("/test")
public class TestController 
{
	@GetMapping
	public String test()
	{
		List<Test> list = TestService.getAllTestList();
		return "tests";
	}
}
