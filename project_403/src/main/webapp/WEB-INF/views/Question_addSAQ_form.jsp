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
</head>
<body>
	HELLO QUESTION addSAQ FORM PAGE
	<form:form modelAttribute="question" action="Q_addSAQ" method="post" enctype="multipart/form-data">
		<p> 과목명 
		<select name="name_select">
			<option selected>선택</option>
			<%
			for(Subject sub:sub_all_name){	
			%>
				<option><%=sub.getSub_name()%></option>
			<%
			}
			%>
		</select>
		<select name="chap_select">	
			<%	
			for(Subject sub:sub_all_name){
				for(Subject sub_chap : sub_all){
					if(sub.getSub_name_code()==sub_chap.getSub_name_code()){
					%>
						<option><%=sub_chap.getSub_chap()%></option>
					<%
					}
				}
			}
			%>
		</select>
		<p> 문제분류 	<form:radiobutton path="question_id" value="MCQ" disabled="true"/>
						<label for="MCQ">객관식</label>
					<form:radiobutton path="question_id" value="SAQ" checked="checked"/>
						<label for="SAQ">주관식</label>
					<form:radiobutton path="question_id" value="CP" disabled="true"/>
						<label for="CP">코딩</label>
		<p> 문제 내용 <form:input path="question_content" style="width:300px; height:100px;"/>
		<p> 정답<form:input path="question_ans"/>
		<p> 문제 이미지 <form:input type="file" path="question_img"/>
		<p> 문제풀이 추가 카운트 <form:input path="question_plus"/>
		<p> 문제풀이 횟수 <form:input path="question_count"/>

		<p> <input type="submit" value="전송">
	</form:form>
</body>
</html>