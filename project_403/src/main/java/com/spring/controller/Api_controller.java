package com.spring.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class Api_controller {
	
	//api 정보를 조회하는 기능
	@GetMapping
	public String api() {
	try {
		
		URL url=new URL("http://apis.data.go.kr/B490007/qualExamSchd/getQualExamSchdList?dataFormat=json&serviceKey=FXwNLM1ohPkhVezkTGCxF2HGzLzJ1Xa7%2BBXmCQUw8y2%2BjgB2QY3TQLGe%2F%2B2Qn0R0AJq9vtX7x7OxlMK2CleNsA%3D%3D&implYy=2024&numOfRows=10&pageNo=1&jmCd=7910&qualgbCd=T");
		HttpURLConnection conn=(HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		
		BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
		StringBuilder resp=new StringBuilder();
		String line;
		while((line=br.readLine())!=null) {
			resp.append(line);
		}
		System.out.println(resp);
		br.close();
		
		}catch(Exception e) {e.printStackTrace();}
		return null;
	}
}
