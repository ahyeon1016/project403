<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.spring.domain.Subject" %>
<%
	Subject sub_chap = (Subject) request.getAttribute("subByChap");
	Subject inputSub = (Subject) request.getAttribute("inputSub");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	HELLO SUBJECT SUBJECT BY CHAP
	<%
	if(sub_chap.getSub_name()!=null){ 
	%>
		<h3>과목 이름 <%=sub_chap.getSub_name()%></h3>
		<%
		if(sub_chap.getSub_chap()!=null){
		%>
			<p>문제 코드 <%=sub_chap.getSub_code()%>
			<p>과목 챕터 <%=sub_chap.getSub_chap()%>
			<p>과목 번호 <%=sub_chap.getSub_num()%>
		<%
		}else{
		%>
			<h3><%=inputSub.getSub_chap()%> 챕터가 존재하지 않습니다.</h3>
		<%}%>
	<%
	}else{
	%>
		<h3> <%=inputSub.getSub_name()%>과목이 존재하지 않습니다.</h3>
	<%}%>
</body>
</html>