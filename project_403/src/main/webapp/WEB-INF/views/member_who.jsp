<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="update?mem_id=${member.getMem_id()}" method="post">
<table>
<tr>
<td>아이디:${member.getMem_id()}</td>
</tr>
<tr>
<td>닉네임:${member.getMem_nickName()}</td>
</tr>
<tr>
<td>포인트:${member.getMem_point()}</td>
</tr>
<tr>
<td>경험치:${member.getMem_exp()}</td>
</tr>
</table>
<button type="submit" >정보수정</button>
</form>


</body>
</html>