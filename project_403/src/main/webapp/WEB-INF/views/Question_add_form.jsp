<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	HELLO
	<form:form modelAttribute="question" action="add" method="post" enctype="multipart/form-data">
		<p> 문제 내용 <form:input path="question_inner"/>
		<p> 정답 <form:input path="question_ans"/>
		<p> 문제 이미지 <form:input type="file" path="question_img"/>
		<p> 문제풀이 추가 카운트 <form:input path="question_plus"/>
		<p> 문제풀이 횟수 <form:input path="question_count"/>
		<p> 문제분류 <form:input path="question_chap"/>
		<p> <input type="submit" value="전송">
	</form:form>
</body>
</html>