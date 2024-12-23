<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.spring.domain.Member" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body>
	<%@include file="/WEB-INF/views/member_home.jsp" %>


<form:form modelAttribute="member" action="update/sequence" method="post" id="form" enctype="multipart/form-data">
	 
	<p id="id_filed">아이디<form:input path="mem_id" type="text" value="${member.getMem_id()}" readonly="true" id="mem_id"/></p>
	<p id="pw_filed">비밀번호<form:input path="mem_pw" type="password"  minlength="3" maxlength="15" required="true" id="pw"/> 비밀번호는 3~15자 이내여야 합니다.</p>
	<p>닉네임<form:input path="mem_nickName" type="text" maxlength="10" value="${member.getMem_nickName()}" readonly="true"/> 닉네임 변경권을 쓸 경우 바꿀 수 있습니다.</p>
	<p>이메일<form:input path="mem_email" type="email" value="${member.getMem_email()}" id="email" required="true" /></p>
	<p>프로필 사진<form:input path="mem_profile" id="input_file" type="file" accept="image/*"/> </p>
	<form:button type="submit" id="sub">수정하기</form:button>
</form:form>
<script>
	//폼에 입력된 값 유효성 검사
	let sub=document.querySelector("#sub");
	sub.addEventListener("click",alertmessage);
	const emailPattern = /^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$/;
	function alertmessage(event){
		let email=document.querySelector("#email").value;
		let pw=document.querySelector("#pw").value;
		let file=document.querySelector("#input_file").file;
		console.log(pw.length);
		console.log(emailPattern.test(email));
		event.preventDefault();
		if(
			    (pw.length >= 3 && pw.length <= 15 && emailPattern.test(email)) || 
			    ((mem_id.value.startsWith("kakao_") || mem_id.value.startsWith("naver_")))
			){
		alarm();
		document.querySelector("#form").submit();
		}else{
			alert ("형식에 맞게 입력해주세요.");
		}
	}
	function alarm(){
		alert("수정되었습니다.다시 로그인 하십시오.");
	}
	
	//카카오나 네이버로 로그인 했을 시 비밀번호 설정 못하게 하기
	let mem_id=document.querySelector("#mem_id");
	let mem_pw=document.querySelector("#pw");
	let pw_filed=document.querySelector("#pw_filed")
	if((mem_id.value.startsWith("kakao_")||mem_id.value.startsWith("naver_"))&&pw.value===""){
		pw_filed.style.display="none";
	}
	//올린 파일 유효성 검사
	let file=document.querySelector("#input_file");
	file.addEventListener("change",extention);
	function extention(){
		let imgs=file.files[0];
		if(imgs.type!="image/jpeg"&&imgs.type!="image/png"){
			alert("jpg,png 파일만 업로드 가능합니다.");
			file.value="";
		}
		
	}
	
</script>
</body>
</html>