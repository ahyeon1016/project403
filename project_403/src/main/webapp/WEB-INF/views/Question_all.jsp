<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.spring.domain.Question"%>
<%@ page import="com.spring.domain.Subject"%>
<%
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
	HELLO QUESTION ALL PAGE
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
	<select name="chap_select" id="chap_selector">
		<option>선택</option>
	</select> | 
	전체 <input type="radio" name="id" value="ALL" class="question_id" checked disabled="true">
	객관식<input type="radio" name="id" value="MCQ" class="question_id" disabled="true">
	주관식<input type="radio" name="id" value="SAQ" class="question_id" disabled="true">
	코딩<input type="radio" name="id" value="CP" class="question_id" disabled="true">
	<%
	if(member!=null){ 
	%>
	|자기가 낸 문제 찾기 <input type="checkbox" name="name" value="<%=member.getMem_serial()%>" id="myQuestion">
	<%
	}
	%>
	
	<!-- 문제 목록이 출력되는 div -->
	<div id="question_container">
		<div>과목과 챕터를 선택 해주세요</div>
	</div>
	
	<!-- 페이징 버튼이 출력되는 div -->
	<div id="question_paging">
		
	</div>
	<script src="/project_403/resources/js/questionAll.js"></script>
</body>
</html>