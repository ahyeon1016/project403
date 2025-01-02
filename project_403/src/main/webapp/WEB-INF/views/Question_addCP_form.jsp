<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.spring.domain.Subject"%>
<%
	ArrayList<Subject> sub_all = (ArrayList<Subject>) request.getAttribute("sub_all");
	ArrayList<Subject> sub_all_name = (ArrayList<Subject>) request.getAttribute("sub_all_name");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
	<%@include file="/WEB-INF/views/member_home.jsp" %>
	HELLO QUESTION addCP FORM PAGE
	<p>${member.getMem_serial()}
	<form:form modelAttribute="question" action="Q_addCP" method="post" enctype="multipart/form-data" onsubmit="return validateCPForm(event)">
		<p> 과목명 
		<select name="name_select" id="name_selector">
			<option selected>선택</option>
			<%
			for(Subject sub:sub_all_name){	
			%>
				<option><%=sub.getSub_name()%></option>
			<%
			}
			%>
		</select>
		<select name="chap_select" id="chap_selector"></select>
		<p> 문제분류 	<form:radiobutton path="question_id" value="MCQ" disabled="true"/>
						<label for="MCQ">객관식</label>
					<form:radiobutton path="question_id" value="SAQ" disabled="true"/>
						<label for="SAQ">주관식</label>
					<form:radiobutton path="question_id" value="CP" checked="checked"/>
						<label for="CP">코딩</label>
		<p> 문제 내용 <textarea name="question_content_text" rows="10" cols="70"></textarea>
		<p> 코드 내용 <form:textarea path="question_content" rows="20" cols="70"/>
		<p> 정답<form:input path="question_ans"/>
		<p> 문제 이미지 <form:input type="file" path="question_img"/>
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

		<p> <input type="submit" value="전송">
	</form:form>
	<script src="/project_403/resources/js/questionAdd.js"></script>
</body>
</html>