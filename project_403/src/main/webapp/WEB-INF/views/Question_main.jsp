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
	HELLO QUESTION MAIN
	<p>${member.getMem_serial()}
	<hr>
	<%
	if(member!=null){
	%>
		<div>
			<img src="/project_403/resources/images/1.jpg" style="width: 10%; height: 10%">
			<p><button onclick="addQuestion('MCQ')">객관식 문제 만들기</button><p>
		</div>
		<div>
			<img src="/project_403/resources/images/2.jpg" style="width: 10%; height: 10%">
			<p><button onclick="addQuestion('SAQ')">주관식 문제 만들기</button><p>
		</div>
		<div>
			<img src="/project_403/resources/images/3.jpg" style="width: 10%; height: 10%">
			<p><button onclick="addQuestion('CP')">코딩 문제 만들기</button><p>
		</div>
		<br>
		<p><a href="Q_all">모든 문제 확인하기</a>
	<%} else{%>
		<div>로그인을 하시오.</div>
	<%} %>	
	<hr>
	
	<script>
		function addQuestion(type){
			window.location.href="Q_add"+type;
		}
	</script>
</body>
</html>