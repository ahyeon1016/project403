<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.spring.domain.Subject" %>
<%
	ArrayList<Subject> sub_all = (ArrayList<Subject>) request.getAttribute("sub_all");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	HELLO SUBJECT ALL
	<%for(Subject sub : sub_all){ %>
		<h3>과목 이름 <%=sub.getSub_name()%></h3>
		<p>문제 코드 <%=sub.getSub_code()%>
		<p>과목 챕터 <%=sub.getSub_chap()%>
		<p>과목 번호 <%=sub.getSub_num()%>
	<%} %>
</body>
</html>