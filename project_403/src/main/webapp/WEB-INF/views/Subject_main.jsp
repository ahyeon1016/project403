<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	HELLO SUBJECT
	<p><a href="sub/sub_form">Subject작성폼 페이지 이동</a>
	<p><a href="sub/sub_all">Subject 목록</a>
	<form:form action="sub/getSubByName" method="get">
		<h3>찾고싶은 Subject</h3>
		<p>sub_name : <input type="text" name="sub_name">
		<p><input type="submit" value="전송">
	</form:form>
</body>
</html>