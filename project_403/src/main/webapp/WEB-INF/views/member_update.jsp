<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form:form modelAttribute="member" action="update/sequence" method="post" id="form" enctype="multipart/form-data">
	아이디<form:input path="mem_id" type="text" value="${member.getMem_id()}" readonly="true" /><br>
	비밀번호<form:input path="mem_pw" type="password"  minlength="3" maxlength="15" required="true" id="pw"/> 비밀번호는 3~15자 이내여야 합니다.<br>
	닉네임<form:input path="mem_nickName" type="text" maxlength="10" value="${member.getMem_nickName()}"/><br>
	이메일<form:input path="mem_email" type="email" value="${member.getMem_email()}" id="email" required="true" /><br>
	사진 등록<form:input path="mem_profile" type="file"/> <br>
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
		if((pw.length>=3&&pw.length<=15)&&(emailPattern.test(email))){
		alarm();
		document.querySelector("#form").submit();
		}else{
			alert ("형식에 맞게 입력해주세요.");
		}
	}
	function alarm(){
		alert("수정되었습니다.다시 로그인 하십시오.");
	}
</script>
</body>
</html>