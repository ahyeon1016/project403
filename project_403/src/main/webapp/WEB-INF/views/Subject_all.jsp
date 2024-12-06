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
	<%
	if(sub_all.size()!=0){
		for(Subject sub : sub_all){ 
	%>
		<h3>과목 이름 <%=sub.getSub_name()%> | 
			<a href="updateSubName?sub_name=<%=sub.getSub_name()%>">수정하기</a> | 
			<a href="deleteSubName?sub_name=<%=sub.getSub_name()%>">삭제하기</a>
		</h3>
		<p>과목 코드 <%=sub.getSub_name_code()%>
		<p>챕터 이름 <%=sub.getSub_chap()%> | 
			<a href="updateSubChap?sub_name=<%=sub.getSub_name()%>&sub_chap=<%=sub.getSub_chap()%>">수정하기</a> | 
			<a href="deleteSubChap?sub_name=<%=sub.getSub_name()%>&sub_chap=<%=sub.getSub_chap()%>">삭제하기</a>
		</p>
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