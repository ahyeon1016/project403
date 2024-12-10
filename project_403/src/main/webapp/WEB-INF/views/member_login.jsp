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
<a href="https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=dYy4lyrqfeCoc7Q3NRsE&state=STATE_STRING&redirect_uri=http://localhost:8080/project_403/member/login/naver">네이버로 로그인하기</a>
<a href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=17731f8bc4cf09ad45f06addfd541982&redirect_uri=http://localhost:8080/project_403/member/login/kakao">카카오로 로그인하기</a>
<a href="./">뒤로가기</a>
</form:form>
</body>
</html>