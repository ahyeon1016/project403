<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form:form modelAttribute="member" action="update/sequence" method="post">
	아이디<form:input path="mem_id" type="text" placeholder="${member.getMem_id()}" readonly="true" /><br>
	비밀번호<form:input path="mem_pw" type="password" placeholder="비밀번호는 3~15자 이내의 값입니다."  minlength="3" maxlength="15"/><br>
	닉네임<form:input path="mem_nickName" type="text" placeholder="${member.getMem_nickName()}"/><br>
	<form:button type="submit" id="sub">수정하기</form:button>
</form:form>
<script>
document.querySelector("#id").addEventListner("click",alert);
function alert(){
	setTimeout(function(){
		alert("수정되었습니다.다시 로그인 하십시오.");	
	},500);
	
}

</script>
</body>
</html>