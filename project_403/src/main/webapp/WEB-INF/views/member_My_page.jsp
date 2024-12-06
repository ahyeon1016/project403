<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="com.spring.domain.Member" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MyPage</title>
</head>
<body>
<%session = request.getSession(false); 
	Member member=(Member)session.getAttribute("member");%>
	
	${member.getMem_nickName()}님 환영합니다.
<form action="member?mem_id=${member.getMem_id()}" method="post">
<button type="submit">정보조회</button>
</form>
<form action="delete?mem_id=${member.getMem_id()}" method="post">
<button type="submit">회원 탈퇴</button>
</form>
<a href="logout">로그아웃</a>
<a href="./">홈으로</a>

</body>
</html>