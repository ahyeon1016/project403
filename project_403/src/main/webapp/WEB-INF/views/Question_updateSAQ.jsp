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
	HELLO QUESTION updateSAQ FORM PAGE!
	<form:form modelAttribute="question" action="../Q_updateSAQ" method="post" enctype="multipart/form-data">
		<p> <form:hidden path="mem_serial"/>
		<p> <form:input path="sub_code_sum" readonly="true"/>
		<p> <form:input path="question_serial" readonly="true"/>
		<p> 문제분류 	<form:radiobutton path="question_id" value="SAQ" checked="checked"/>
						<label for="SAQ">주관식</label>
		<p> 문제 내용 <form:input path="question_content" style="width:300px; height:100px;"/>
		<p>	정답	<form:input path="question_ans"/>

		<p><img src="/project_403/resources/images/${question.getQuestion_img_name()}">
		<p> 문제 이미지 
		<p><form:input type="file" path="question_img"/>
		<p> 문제 난이도<form:radiobutton path="question_level" value="1"/>
						<label for="1">1</label>
					<form:radiobutton path="question_level" value="2"/>
						<label for="2">2</label>
					<form:radiobutton path="question_level" value="3"/>
						<label for="3">3</label>
					<form:radiobutton path="question_level" value="4"/>
						<label for="4">4</label>
					<form:radiobutton path="question_level" value="5"/>
						<label for="5">5</label>
		<p> 문제풀이 횟수 <form:input path="question_count" disabled="true"/>

		<p> <input type="submit" value="전송">
	</form:form>
</body>
</html>