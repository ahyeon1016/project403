<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%@include file="/WEB-INF/views/member_home.jsp" %>
	HELLO SUBJECT
	<p>${member.getMem_id()}
	<p><a href="sub/sub_name_form">Subject name 작성 폼 페이지 이동</a>
	<p><a href="sub/sub_chap_form">Subject chap 작성 폼 페이지 이동</a>
	<p><a href="sub/sub_all">Subject 목록</a>
	<form:form action="sub/getSubByName" method="get">
		<h3>찾고싶은 Subject의  sub_name</h3>
		<p>sub_name : <input type="text" name="sub_name">
		<p><input type="submit" value="전송">
	</form:form>
	<br>
	<div>
		<form:form modelAttribute="subject" action="sub/getSubByChap" method="get">
			<h3>찾고싶은 Subject의  sub_chap</h3>
			<p>sub_name : <form:input path="sub_name"/>
			<p>sub_chap : <form:input path="sub_chap"/>
			<p><input type="submit" value="전송">
		</form:form>
	</div>
</body>
</html>