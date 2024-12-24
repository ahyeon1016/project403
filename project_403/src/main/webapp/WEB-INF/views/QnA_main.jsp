<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%@include file="/WEB-INF/views/member_home.jsp" %>
	<p>${member.getMem_nickName()}
	HELLO QNA MAIN
	<hr>
	<p><a href="commentRootAll?page=1">모든 질문(root) 확인하러가기</a>
</body>
</html>