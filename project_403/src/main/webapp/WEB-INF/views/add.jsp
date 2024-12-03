<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<script src="http://code.jquery.com/jquery-latest.min.js"></script> <%--아작스 사용을 위한 코오드 --%>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form:form modelAttribute="member" action="./new" method="post">
아이디<form:input path="mem_id" id="mem_id"/><button type="button" id="check_id">중복 검사</button><br>
비밀번호<form:input path="mem_pw" id="mem_pw" type="password"/><br>
닉네임<form:input path="mem_nickName" id="mem_nickName"/><br>
<button type="submit">회원 가입하기</button>
</form:form>


<script>

document.querySelector("#check_id").addEventListener("click",memck);
function memck(){
	console.log("memck함수 실행");
	let mem_id=document.querySelector("#mem_id").value;

	$.ajax({
		url:"check",
		type:"post",
		data:JSON.stringify({"mem_id":mem_id}),
		contentType:"application/json",
		success:function(data){
			alert(data);
		},
		error:function(errorThrown){
			alert("fail");
		}
	});
	
}
</script>
</body>
</html>