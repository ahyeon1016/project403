<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- 세션에 멤버가 있을때/없을때 나타나는 정보 분리. -->
<%if(session.getAttribute("member")==null){ %>
<form action="add" method="get">
    <button type="submit">회원가입</button>
</form>
<form action="login" method="get">
    <button type="submit">로그인</button>
</form>    
<%}else{ %>
<form action="mypage" method="get">
	<button type="submit">마이페이지</button>	
</form>
<%} %>
</body>
</html>