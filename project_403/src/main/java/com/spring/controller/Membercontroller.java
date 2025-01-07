package com.spring.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.domain.Member;
import com.spring.domain.Member_Item;
import com.spring.service.FnoteService;
import com.spring.service.MemberItemService;
import com.spring.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("member")
public class Membercontroller {
	
	@Autowired
	FnoteService fnoteservice;
	
	@Autowired
	MemberService memberservice;
	
	@Autowired
	MemberItemService memberitemservice;
	
	@Autowired
	MailSender sender;

	
	//기본 매핑
	@GetMapping
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
		String regex = "^[a-zA-Z0-9]+$";
		System.out.println(map.get("mem_id"));
		Member member=memberservice.getMyInfo((String)map.get("mem_id"));
		if(member==null) {
			 value="사용 가능한 아이디입니다.";
			 isavail=true;
			 if(map.get("mem_id").toString().startsWith("naver_")||
					 map.get("mem_id").toString().startsWith("kakao_")||
					 !(map.get("mem_id").toString().matches(regex))||
					 (map.get("mem_id").toString().length()<3)) {
					value="잘못된 형식입니다.";
					isavail=false;
				}
		}
		
		else {
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
			String nick_pattern = "^[가-힣a-zA-Z0-9]+$";
			System.out.println(pw_length);
			if (
				    pw_length <= 15 && pw_length >= 3 &&
				    Pattern.matches(email_pattern, email) &&
				    pw.equals(pw_sub) &&
				    !map.get("mem_id").toString().startsWith("naver_") &&
				    !map.get("mem_id").toString().startsWith("kakao_") &&
				    Pattern.matches(nick_pattern, map.get("mem_nick").toString())
				)
 { //형식에 맞게 입력했을 시 코드 실행
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
	@GetMapping("login")
	public String Login_page(@ModelAttribute("member") Member member) {
		return "member_login";
	}
	
	//세션에 member_login에서 받은 멤버 정보 담으며 로그인하기.
	@PostMapping("login")
	public String login(@ModelAttribute("member") Member member,HttpServletRequest req) {
		HttpSession session=req.getSession();
		member=memberservice.member_login(member);
		if(member!=null) {
			Member_Item mi=memberitemservice.mem_item_info(member.getMem_id());
			System.out.println("logiiiiiiiiiiiiiiiiiiiiin"+member.getMem_nickName());
			session.setAttribute("member", member);
			session.setAttribute("member_item", mi);
			return "member_My_page";
		}
		else {
			return "member_login_fail";
		}
	}

	//카카오로 로그인하기
	@GetMapping("login/kakao")
	public String kakao_login(@RequestParam String code,HttpServletRequest req) {
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
	    params.add("redirect_uri", "http://wjdwoals222.cafe24.com/member/login/kakao");
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
	    String user_id=userInfo.get("id").toString();
	    String email = (String) kakaoAccount.get("email");
	    String nickname = (String) profile.get("nickname");
	    String profile_img=profile.get("profile_image_url").toString();
	    // 로그 출력 또는 추가 처리
	    System.out.println("User ID: " + user_id);
	    System.out.println("Email: " + email);
	    System.out.println("Nickname: " + nickname);
	    System.out.println(profile_img);
	    System.out.println(profile);
	    // 여기서 사용자 정보를 데이터베이스에 저장하거나 세션 처리 등을 진행
	    Member member=new Member();
	    member.setMem_id("kakao_"+user_id);
	    member.setMem_nickName(nickname);
	    member.setMem_profile_name(profile_img);
	    if(memberservice.kakao_info(member)) {
			//카카오톡에 정보가 존재할 경우
	    	member=memberservice.getMyInfo(member.getMem_id());
	    	Member_Item mi=memberitemservice.mem_item_info(member.getMem_id());
	    	HttpSession session=req.getSession();
	    	session.setAttribute("member", member);
	    	session.setAttribute("member_item", mi);
	    }else {
	    	//아닐 경우
	    	memberservice.addMember(member);
            memberitemservice.addMemItem(member);
            Member_Item mi=memberitemservice.mem_item_info(member.getMem_id());
            HttpSession session=req.getSession();
            session.setAttribute("member", member);
            session.setAttribute("mi", mi);
	    }
	    
	    
	    return "redirect:/";
	}
	
	//네이버로 로그인하기
	@GetMapping("login/naver")
	
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
			member=memberservice.getMyInfo(member.getMem_id());
			Member_Item mi=memberitemservice.mem_item_info(member.getMem_id());
			HttpSession session=req.getSession();
			session.setAttribute("member", member);
			session.setAttribute("member_item", mi);
			
		}else {
			memberservice.addMember(member);
			memberitemservice.addMemItem(member);//멤버 아이템 데이터 추가

			System.out.println("네이버 계정의 회원정보가 없어용");
			HttpSession session=req.getSession();
			session.setAttribute("member", member);
			
		}
		
	}catch (Exception e) {e.printStackTrace();}
		return "member_My_page";
	}
	

	//member_who 페이지에서 dto를 가지고 member_update 폼 페이지로 이동
	@PostMapping("update")
	public String update_member(@ModelAttribute("member") Member member,@RequestParam String mem_id,Model model) {
			member=memberservice.getMyInfo(mem_id);
			model.addAttribute(member);
			return "member_update";
	}
	
	//받은 회원 아이디를 가지고 아이템 페이지로 이동.
	@PostMapping("item")
	public String mem_item(@RequestParam String mem_id,Model model) {
		Member_Item mi=memberitemservice.mem_item_info(mem_id);
		model.addAttribute("mem_item",mi);
		return "member_Items";
	}

	//model에 member dto를 넣고 닉네임 변경 페이지로 이동
	@PostMapping("item/nick")
	public String mem_nickname(@RequestParam String mem_id,Model model) {
		System.out.println("닉네임 페이지의 멤버아이디"+mem_id);
		Member mb=new Member();
		mb=memberservice.getMyInfo(mem_id);
		System.out.println("아이디가 있다~~~~~~~~~~~~~~~~~~~"+mb.getMem_id());
		model.addAttribute("member", mb);
		return "member_nick";
	}
	
	//닉네임 변경을 눌렀을 때 기능
	@PostMapping("item/nick/change")
	public String mem_nick_change(@RequestParam String mem_id,HttpServletRequest req,Model model) {
		HttpSession session=req.getSession(false);
		
		String nick=(String)req.getParameter("nick");
		//id를 통한 회원정보 조회
		Member mb=(Member)session.getAttribute("member");
		//닉네임 변경 함수
		mb.setMem_nickName(nick);
		memberservice.mem_nickname_change(mb);
		//아이템에서 닉변권 제거 함수
		memberitemservice.nick_change(mem_id);
		mb.setMem_nickName(nick);
		session.setAttribute("member", mb);
		return "member_My_page";
	}
	
	//model에 Member dto를 넣고 닉네임 색상 변경 페이지로 이동
	@PostMapping("item/font")
	public String mem_font(@RequestParam String mem_id,Model model) {
		Member_Item mi=new Member_Item();
		mi=memberitemservice.mem_item_info(mem_id);
		Member mb=memberservice.getMyInfo(mem_id);
		model.addAttribute("member",mb);
		model.addAttribute("member_item",mi);
		return "member_font";
	}

	//닉네임 색상 변경
	@PostMapping("item/font/change")
	public String mem_font_change(@RequestParam String mem_id,HttpServletRequest req) {
		HttpSession session=req.getSession(false);
		Member_Item mi=new Member_Item();
		String color=req.getParameter("color");
		System.out.println(color);
		mi=memberitemservice.mem_item_info(mem_id);
		mi.setMem_color(color);
		memberitemservice.color_change(mi);
		session.setAttribute("member_item", mi);
		return "member_My_page";
	}
	
	//닉변권 구매
	@PostMapping("item/purchase_nick")
	public String mem_nick_purchase(@RequestParam String mem_id,HttpServletRequest req) {
		HttpSession session=req.getSession(false);
		Member member=(Member)session.getAttribute("member");
		int point=50;
		memberservice.item_buy(point, mem_id);
		memberitemservice.nick_change_buy(mem_id);
		member.setMem_point(member.getMem_point()-point);
		
		session.setAttribute("member", member);
		return "redirect:/";
	}
	
	//닉네임 색상변경권 구매
	@PostMapping("item/purchase_font")
	public String mem_font_purchase(@RequestParam String mem_id,HttpServletRequest req) {
		HttpSession session=req.getSession(false);
		Member member=(Member)session.getAttribute("member");
		int point=100;
		memberservice.item_buy(point, mem_id);
		memberitemservice.nick_color_buy(mem_id);
		member.setMem_point(member.getMem_point()-point);
		session.setAttribute("member", member);
		
		return "redirect:/";
	}
	
	
	
	//member_update 폼페이지에서 받은 데이터를 member 객체에 삽입 후 session을 종료해 다시 로그인 하게 만듬
	@PostMapping("update/sequence")
	public String update_sequence(@ModelAttribute("member")Member member,HttpServletRequest req) {
		HttpSession session=req.getSession(false);
		System.out.println(member.getMem_id());
		System.out.println(member.getMem_profile());
		String path=session.getServletContext().getRealPath("/resources/images");
		System.out.println(path);
		
		Member mb=(Member)session.getAttribute("member");
		MultipartFile multi=member.getMem_profile();
		DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
		String dt=LocalDateTime.now().format(formatter);
		String filename=("RLPL_profile_"+dt+"_"+multi.getOriginalFilename());
		File file=new File(path,filename);
		try {
			multi.transferTo(file);
		} catch(Exception e) {e.printStackTrace();}
		member.setMem_profile_name(filename);
		
		memberservice.member_update(member);
		mb.setMem_profile_name(filename);
		session.setAttribute("member", mb);
		return "redirect:/";
	}
	
	//member_My_page에서 회원탈퇴를 누를 시 회원 탈퇴 페이지 이동
	@PostMapping("delete")
	public String byebye_member(@RequestParam String mem_id,Model model) {
		model.addAttribute("mem_id",mem_id);
		return "member_bye";
	}
	
	//session에 저장된 member객체의 id와 input에 입력된 pw를 가지고 delete 실행
	@PostMapping("delete_bye")
	public String bye(HttpServletRequest req) {
		HttpSession session=req.getSession(false);
		Member member=(Member)session.getAttribute("member");
		ArrayList arr=fnoteservice.note_mine(member.getMem_id());
		if(arr!=null) {
			fnoteservice.all_note_delete(member.getMem_id());
		}
		memberitemservice.item_bye(member);
		memberservice.member_delete(member);

		session.invalidate();
		return "redirect:/";
	}
		
	//마이페이지로 이동
	@GetMapping("/mypage")
	public String My_page() {
		return "member_My_page";
	}
	
	//관리자 페이지로 이동
	@PostMapping("/admin")
	public String admin_page(@RequestParam("page") int page,Model model) {
		ArrayList<Member> al=new ArrayList<Member>();
		int limit_num=(page-1)*10;
		int mem_num=memberservice.mem_num();
		al=memberservice.read_all_Member(limit_num);
		Member mb=al.get(0);
		System.out.println("첫번째 멤버의 아이디를 조회한다."+mb.getMem_id());
		model.addAttribute("al",al);
		model.addAttribute("mem_num",mem_num);
		return "member_admin";
	}
	
	//이메일 전송
	@ResponseBody
	@PostMapping("email")
	public HashMap<String,Object> email_send(@RequestBody HashMap<String,Object> map){
		HashMap<String,Object> returnmap=new HashMap<String,Object>();
		String user_mail=(String)map.get("user_mail");
		String user_id=(String)map.get("user_id");
		System.out.println("이메일 접속 완료!!!!!!!!!!!!!!!!!"+user_mail);
		String host="http://wjdwoals222.cafe24.com/member/email/checked";
		String from="rlpl4033@gmail.com";
		String to=user_mail;
		int mem_serial=memberservice.mem_serial(user_mail,user_id);
		String content="클릭하여 이메일 인증을 완료해주세요!\n"+host+"?mem_serial="+mem_serial;
		SimpleMailMessage ms=new SimpleMailMessage();
		ms.setTo(to);
		ms.setSubject("랠리폴리 이메일 인증메세지");
		ms.setText(content);
		ms.setFrom(from);
		sender.send(ms);
		return returnmap;
	}
	
	//이메일 인증
	@GetMapping("email/checked")
	public String check_email(@RequestParam int mem_serial) {
		System.out.println("이메일 인증 완료를 했다"+mem_serial);
		memberservice.mem_confirm(mem_serial);
		return "redirect:/";
	}
	
	//알림 발동
	@GetMapping("alarm")
	public String active_alarm() {
		memberservice.mem_alarm_add("qwer", "asdf", 1);
		return "redirect:/";
	}
	
	//알림 확인 및 삭제
	@GetMapping("alarm/delete")
	public String alarm_delete(@RequestParam int index,HttpServletRequest req) {
		System.out.println(index);
		ArrayList<String> arr=new ArrayList<String>();
		HttpSession session=req.getSession(false);
		Member member=(Member)session.getAttribute("member");
		String mem_alarm=member.getMem_alarm();
		System.out.println(member.getMem_alarm());
		String[] mem_alarm_split=mem_alarm.split(",");
		for(int i=0;i<mem_alarm_split.length;i++) {
			arr.add(mem_alarm_split[i]);
			System.out.println(arr.get(i));
		}
		arr.remove(index);
		String alarm=String.join(",",arr);
		System.out.println(alarm);
		member.setMem_alarm(alarm);
		member.setAlarm_list(arr);
		memberservice.mem_alarm_update(member);
		session.setAttribute("member", member);
		return "member_My_page";
	}
	
	//로그아웃
	@GetMapping("logout")
	public String logout(HttpServletRequest req) {
		HttpSession session=req.getSession(false);
		session.invalidate();
		return "redirect:/";
	}
}
