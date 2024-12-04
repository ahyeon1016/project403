<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form:form modelAttribute="member" action="./login" method="post">
아이디<form:input path="mem_id" id="mem_id"/><br>
비밀번호<form:input path="mem_pw" id="mem_pw" type="password"/><br>
<button type="submit">로그인</button>
</form:form>
</body>
</html>