<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>탈퇴 페이지</title>
</head>
<body>
<p>${mem_id}님 정말로 탈퇴하시겠습니까?</p>
<p>탈퇴를 하시려면 비밀번호를 입력해 주세요</p>
<form action="delete_bye" method="post">
비밀번호	<input type="password" name="pw">
<input type="submit" value="탈퇴하기"><br>
<a href="/project_403">취소하기</a>
</form>
</body>
</html>