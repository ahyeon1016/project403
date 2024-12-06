<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 페이지</title>
</head>
<body>
<form:form modelAttribute="member" action="./login" method="post">
아이디<form:input path="mem_id" id="mem_id"/><br>
비밀번호<form:input path="mem_pw" id="mem_pw" type="password" required="true"/><br>
<button type="submit">로그인</button>
<a href="./">뒤로가기</a>
</form:form>
</body>
</html>