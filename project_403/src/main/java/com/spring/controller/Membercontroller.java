package com.spring.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.domain.Member;
import com.spring.repository.MemberRepository;
import com.spring.service.MemberItemService;
import com.spring.service.MemberService;

@Controller
@RequestMapping
public class Membercontroller {
	
	@Autowired
	MemberService memberservice;
	
	@Autowired
	MemberItemService memberitemservice;
	
	//기본 매핑
	@GetMapping("/")
	public String home(){
		return "member_home";
	}
	
	//member_add 폼 페이지로 이동
	@GetMapping("/add")
	public String add(@ModelAttribute Member member,Model model)
	{	
		
		
		return "member_add";
	}
	
	//member_add 폼 페이지에서 받은 아이디 중복검사
	@ResponseBody
	@PostMapping("/check")
	public HashMap<String,Object> check_mem(@RequestBody HashMap<String,Object> map) {
		HashMap<String,Object> returnmap=new HashMap<String,Object>();
		String value=null;
		Boolean isavail=false;
		System.out.println(map.get("mem_id"));
		Member member=memberservice.getMyInfo((String)map.get("mem_id"));
		if(member==null) {
			 value="사용 가능한 아이디입니다.";
			 isavail=true;
		}else {
			 value="중복된 아이디입니다.";
			 isavail=false;
		}
		returnmap.put("key", value);
		returnmap.put("isavail", isavail);
		return returnmap;	
	}
	
	//member_add 폼 페이지에서 회원가입을 누를 시 중복 검사 및 회원가입 + MemberItem 테이블에 데이터 추가
		@ResponseBody
		@PostMapping("/finalck")
		public HashMap<String,Object> final_check(@RequestBody HashMap<String,Object> map) {
			HashMap<String,Object> returnmap=new HashMap<String,Object>();
			String value=null;
			Boolean isavail=false;
			System.out.println(map.get("mem_id"));
			String email_pattern="^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
			String email=map.get("mem_email").toString();
			String pw=map.get("mem_pw").toString();
			String pw_sub=map.get("mem_pw_sub").toString();
			int pw_length=pw.length();
			System.out.println(pw_length);
			if((pw_length<=15&&pw_length>=3)&&(Pattern.matches(email_pattern, email))&&pw.equals(pw_sub)) { //형식에 맞게 입력했을 시 코드 실행
			Member member2=memberservice.getMyInfo((String)map.get("mem_id"));
			if(member2==null) {
				 value="회원가입 성공!";
				 isavail=true;
				 Member member=new Member();
				 member.setMem_id((String)map.get("mem_id"));
				 member.setMem_pw((String)map.get("mem_pw"));
				 member.setMem_nickName((String)map.get("mem_nick"));
				 member.setMem_email((String)map.get("mem_email"));
				 memberservice.addMember(member); //회원가입 함수 실행.
				 memberitemservice.addMemItem(member);//멤버 아이템 데이터 추가
				 
			}else 
			{
				 value="중복된 아이디입니다.";
				 isavail=false;
			}
			}else //형식에 맞지않게 입력했을 시 코드 실행
				value="형식에 맞게 입력하세요.";
			returnmap.put("key", value);
			System.out.println(returnmap.get("key"));
			returnmap.put("isavail", isavail);
			return returnmap;	
		}
	
	//member_login 폼 페이지로 이동
	@GetMapping("/login")
	public String Login_page(@ModelAttribute("member") Member member,Model model) {
		model.addAttribute("member",member);				
		return "member_login";
	}
	
	//세션에 member_login에서 받은 멤버 정보 담으며 로그인하기.
	@PostMapping("/login")
	public String login(@ModelAttribute("member") Member member,Model model,HttpServletRequest req) {
		HttpSession session=req.getSession();
		member=memberservice.member_login(member);
		
		if(member!=null) {
			System.out.println("logiiiiiiiiiiiiiiiiiiiiin"+member.getMem_nickName());
			session.setAttribute("member", member);
			return "member_My_page";
		}
		else {
			return "member_login_fail";
		}
	}
	//카카오로 로그인하기
	@GetMapping("/login/kakao")
	public String kakao_login(@RequestParam String code) {
		System.out.println("카카오 로그인 실행");
	    // Jackson ObjectMapper 사용
	    ObjectMapper objectMapper = new ObjectMapper();

	    // 액세스 토큰 요청
	    RestTemplate restTemplate = new RestTemplate();
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

	    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	    params.add("grant_type", "authorization_code");
	    params.add("client_id", "17731f8bc4cf09ad45f06addfd541982");
	    params.add("redirect_uri", "http://localhost:8080/project_403/login/kakao");
	    params.add("code", code);

	    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

	    ResponseEntity<String> tokenResponseString = restTemplate.postForEntity(
	        "https://kauth.kakao.com/oauth/token",
	        request,
	        String.class
	    );

	    // 토큰 정보 파싱
	    Map<String, Object> tokenMap = null;
	    try {
	        tokenMap = objectMapper.readValue(tokenResponseString.getBody(), new TypeReference<Map<String, Object>>() {});
	    } catch (Exception e) {e.printStackTrace();}

	    String accessToken = (String) tokenMap.get("access_token");

	    // 사용자 정보 요청
	    headers = new HttpHeaders();
	    headers.setBearerAuth(accessToken);
	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

	    HttpEntity<String> userInfoRequest = new HttpEntity<>(headers);
	    ResponseEntity<String> userInfoResponseString = restTemplate.postForEntity(
	        "https://kapi.kakao.com/v2/user/me",
	        userInfoRequest,
	        String.class
	    );

	    // 사용자 정보 파싱
	    Map<String, Object> userInfo = null;
	    try {
	        userInfo = objectMapper.readValue(userInfoResponseString.getBody(), new TypeReference<Map<String, Object>>() {});
	    } catch (Exception e) {e.printStackTrace();}

	    // 필요한 사용자 정보 추출
	    Map<String, Object> kakaoAccount = (Map<String, Object>) userInfo.get("kakao_account");
	    Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

	    String email = (String) kakaoAccount.get("email");
	    String nickname = (String) profile.get("nickname");

	    // 로그 출력 또는 추가 처리
	    System.out.println("User ID: " + userInfo.get("id"));
	    System.out.println("Email: " + email);
	    System.out.println("Nickname: " + nickname);
	    System.out.println(profile);
	    // 여기서 사용자 정보를 데이터베이스에 저장하거나 세션 처리 등을 진행
	    return "member_home";
	}
	
	//네이버로 로그인하기
	@GetMapping("/login/naver")
	
	public String naver_login(@RequestParam String code,HttpServletRequest req) {
		try {
		System.out.println("네이버 로그인 실행");
		//토큰 요청
		URL url=new URL("https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&client_id=dYy4lyrqfeCoc7Q3NRsE&client_secret=FlcqD4iNLR&code="+code);
		HttpURLConnection conn=(HttpURLConnection) url.openConnection();//페이지 접속(요청)
		conn.setRequestMethod("GET");//요청 타입
		
		//응답 읽기
		BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));//페이지 내 내용 읽어오기
		StringBuilder resp=new StringBuilder();
		String line;
		while((line=br.readLine())!=null) {
			resp.append(line); //페이지 내 내용 resp에 옮겨담기
		}
		System.out.println(resp);
		br.close();
		
		//JSON 파싱(org.json 라이브러리 사용)
		JSONObject tokenJson=new JSONObject(resp.toString());
		String actoken=tokenJson.getString("access_token"); //JSON 에서 access_token의 value를 추출.
		
		//사용자 정보 요청
		URL userurl=new URL("https://openapi.naver.com/v1/nid/me");
		HttpURLConnection userconn=(HttpURLConnection) userurl.openConnection();//페이지 접속
		userconn.setRequestMethod("GET");
		
		 userconn.setRequestProperty("Authorization", "Bearer " + actoken);
		//응답읽기
		BufferedReader userBr=new BufferedReader(new InputStreamReader(userconn.getInputStream()));
		
		StringBuilder userResp=new StringBuilder();
		String userLine;
		
		while((userLine=userBr.readLine())!=null) {
			userResp.append(userLine);
		}
		userBr.close();
		
		
		System.out.println(userResp);
		JSONObject userJson=new JSONObject(userResp.toString());
		JSONObject userob=userJson.getJSONObject("response");
		String user_id=userob.getString("id");
		String user_nick=userob.getString("nickname");
		String user_email=userob.getString("email");
		String user_name=userob.getString("name");
		
		System.out.println("id"+user_id);
		System.out.println("nick"+user_nick);
		System.out.println("email"+user_email);
		System.out.println("name"+user_name);
		
		//받은 정보를 바탕으로 로그인 하기
		Member member=new Member();
		member.setMem_id("naver_"+user_id);
		member.setMem_email(user_email);
		member.setMem_nickName(user_nick);
		
		if(memberservice.naver_info(member)) { //이미 회원이라면?
			System.out.println("회원 정보가 있어서 로그인할게용");
			HttpSession session=req.getSession();
			session.setAttribute("member", member);
			
		}else {
			memberservice.addMember(member);
			System.out.println("네이버 계정의 회원정보가 없어용");
			HttpSession session=req.getSession();
			session.setAttribute("member", member);
			
		}
		
	}catch (Exception e) {e.printStackTrace();}
		return "member_My_page";
	}
	
	//member_My_page에서 정보조회를 눌렀을때 세션에 있는 mem_id를 받아 member_who로 이동
	@PostMapping("/member")
	public String mem_info(@RequestParam String mem_id,Model model) {
		System.out.println(mem_id+"받아온 멤버 아이디");
		Member member=memberservice.getMyInfo(mem_id);
		System.out.println(member.getMem_id());
		model.addAttribute("member",member);
		return "member_who";
	}
	
	//member_who 페이지에서 dto를 가지고 member_update 폼 페이지로 이동
	@PostMapping("update")
	public String update_member(@ModelAttribute("member") Member member,@RequestParam String mem_id,Model model) {
			member=memberservice.getMyInfo(mem_id);
			model.addAttribute(member);
			return "member_update";
	}
	
	//member_update 폼페이지에서 받은 데이터를 member 객체에 삽입 후 session을 종료해 다시 로그인 하게 만듬
	@PostMapping("update/sequence")
	public String update_sequence(@ModelAttribute("member")Member member,HttpSession session) {
		System.out.println(member.getMem_id());
		memberservice.member_update(member);
		session.invalidate();
		return "member_home";
	}
	
	//member_My_page에서 회원탈퇴를 누를 시 회원 탈퇴 페이지 이동
	@PostMapping("delete")
	public String byebye_member(@RequestParam String mem_id,Model model) {
		model.addAttribute("mem_id",mem_id);
		return "member_bye";
	}
	
	//session에 저장된 member객체의 id와 input에 입력된 pw를 가지고 delete 실행
	@PostMapping("delete_bye")
	public String bye(HttpServletRequest req,HttpSession session) {
		Member member=(Member)session.getAttribute("member");
		Timer timer=new Timer();//객체생성
		long millis=10000;//시간
		TimerTask tt=new TimerTask() { //1주일 후에 실행할 코드		
			@Override
			public void run() {
				System.out.println("되냐");
				memberservice.member_delete(member);
				
			}
		};
		timer.schedule(tt, millis);// 타이머기능
		session.invalidate();
		return "member_home";
	}
		
	//마이페이지로 이동
	@PostMapping("/mypage")
	public String My_page() {
		return "member_My_page";
	}
	
	//로그아웃
	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "member_home";
	}
}
