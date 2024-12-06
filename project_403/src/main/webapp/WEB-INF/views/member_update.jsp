<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form:form modelAttribute="member" action="update/sequence" method="post" id="form">
	아이디<form:input path="mem_id" type="text" placeholder="${member.getMem_id()}" readonly="true" /><br>
	비밀번호<form:input path="mem_pw" type="password"  minlength="3" maxlength="15" required="true" id="pw"/> 비밀번호는 3~15자 이내여야 합니다.<br>
	닉네임<form:input path="mem_nickName" type="text" maxlength="10" placeholder="${member.getMem_nickName()}"/><br>
	<form:button type="submit" id="sub">수정하기</form:button>
</form:form>
<script>
	let sub=document.querySelector("#sub");
	let pw=document.querySelector("#pw");
	sub.addEventListener("click",alertmessage);

	function alertmessage(event){
		event.preventDefault();
		if(pw.value.length>=3&&pw.value.length<=15){
		alarm();
		document.querySelector("#form").submit();
		}else{
			alert ("비밀번호는 3~15자 이내의 값이어야 합니다.")
		}
		console.log(pw.length);
	}
	function alarm(){
		alert("수정되었습니다.다시 로그인 하십시오.");
	}
</script>
</body>
</html>