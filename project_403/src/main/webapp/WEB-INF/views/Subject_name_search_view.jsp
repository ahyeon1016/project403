<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.spring.domain.Subject" %>
<%
	ArrayList<Subject> sub_name_arr = (ArrayList<Subject>) request.getAttribute("sub_name_arr");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	HELLO SUBJECT SUBJECT BY NAME
	<%
	if(sub_name_arr.size()!=0){
		for(Subject sub : sub_name_arr){ 
	%>
		<h3>과목 이름 <%=sub.getSub_name()%></h3>
		<p>과목 코드 <%=sub.getSub_name_code()%>
		<p>챕터 이름 <%=sub.getSub_chap()%>
		<p>챕터 코드 <%=sub.getSub_chap_code()%>
		<p>과목 번호 <%=sub.getSub_num()%>
	<%	} 
	}else{
	%>
		<p>Subject에 데이터가 없습니다.
	<%
	} 
	%>
</body>
</html>