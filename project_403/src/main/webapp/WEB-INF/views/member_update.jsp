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


<form:form modelAttribute="member" action="update/sequence" method="post" id="form" enctype="multipart/form-data">
	 
	<p id="id_filed">아이디<form:input path="mem_id" type="text" value="${member.getMem_id()}" readonly="true" id="mem_id"/></p>
	<p id="pw_filed">비밀번호<form:input path="mem_pw" type="password"  minlength="3" maxlength="15" required="true" id="pw"/> 비밀번호는 3~15자 이내여야 합니다.<p>
	<p>닉네임<form:input path="mem_nickName" type="text" maxlength="10" value="${member.getMem_nickName()}"/></p>
	<p>이메일<form:input path="mem_email" type="email" value="${member.getMem_email()}" id="email" required="true" /></p>
	<p><form:input path="mem_profile" type="file"/> </p>
	<form:button type="submit" id="sub">수정하기</form:button>
</form:form>
<script>
	let sub=document.querySelector("#sub");
	sub.addEventListener("click",alertmessage);
	const emailPattern = /^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$/;
	function alertmessage(event){
		let email=document.querySelector("#email").value;
		let pw=document.querySelector("#pw").value;
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

	let mem_id=document.querySelector("#mem_id");
	let mem_pw=document.querySelector("#pw");
	let pw_filed=document.querySelector("#pw_filed")
	if((mem_id.value.startsWith("kakao_")||mem_id.value.startsWith("naver_"))&&pw.value===""){
		pw_filed.style.display="none";
	}

</script>
</body>
</html>