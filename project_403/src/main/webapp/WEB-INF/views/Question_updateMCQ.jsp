<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="com.spring.domain.Question"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<body>
	<%@include file="/WEB-INF/views/member_home.jsp" %>
	HELLO QUESTION updateMCQ FORM PAGE!
	<form:form modelAttribute="question" action="../Q_updateMCQ" method="post" enctype="multipart/form-data">
		<p> <form:hidden path="mem_serial"/>
		<p> <form:input path="sub_code_sum" readonly="true"/>
		<p> <form:input path="question_serial" readonly="true"/>
		<p> 문제분류 	<form:radiobutton path="question_id" value="MCQ" checked="checked"/>
						<label for="MCQ">객관식</label>
		<p> 문제 내용 <form:input path="question_content" style="width:300px; height:100px;"/>
		<p> 선택지 1<form:input path="question_ans" value="${ans[0]}"/>
		<p> 선택지 2<form:input path="question_ans" value="${ans[1]}"/>
		<p> 선택지 3<form:input path="question_ans" value="${ans[2]}"/>
		<p> 선택지 4<form:input path="question_ans" value="${ans[3]}"/>
		<p>	정답		<form:radiobutton path="question_ans" value="1"/>
						<label for="1">1번</label>
					<form:radiobutton path="question_ans" value="2"/>
						<label for="2">2번</label>
					<form:radiobutton path="question_ans" value="3"/>
						<label for="3">3번</label>
					<form:radiobutton path="question_ans" value="4"/>
						<label for="4">4번</label>
		<p><img src="/project_403/resources/images/${question.getQuestion_img_name()}">
		<p> 문제 이미지 
		<p><form:input type="file" path="question_img"/>
		<p> 문제풀이 추가 카운트 <form:input path="question_plus" disabled="true"/>
		<p> 문제풀이 횟수 <form:input path="question_count" disabled="true"/>

		<p> <input type="submit" value="전송">
	</form:form>
</body>
</html>