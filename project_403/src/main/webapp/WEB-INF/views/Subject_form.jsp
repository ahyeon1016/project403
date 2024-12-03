<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	HELLO SUBJECT FORM
	<form:form modelAttribute="subject" method="POST" action="sub_form">
		<!-- 추후에 ajax로 검색 가능하게 처리할 것임. -->
		<p>과목 이름 : <form:input path="sub_name"/>
		<p>과목 챕터 : <form:input path="sub_chap"/>
		<input type="submit" value="전송">
	</form:form>
</body>
</html>