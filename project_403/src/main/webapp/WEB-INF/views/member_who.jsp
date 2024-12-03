<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
아이디:${member.getMem_id()}<br>
비밀번호:${member.getMem_pw()}<br>
닉네임:${member.getMem_nickName()}<br> 
포인트:${member.getMem_point()}<br>
경험치:${member.getMem_exp()}
</body>
</html>