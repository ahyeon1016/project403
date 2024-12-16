<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	HELLO ADD COMMENT ROOT!
	<hr>
	<form:form modelAttribute="qna" action="../addCommentRoot" method="post">
		<p> 문제 번호 : <form:input path="question_serial" readonly="true"/> 
		<p> 제목 : <form:input path="comment_title"/>
		<p> 내용 : <form:textarea path="comment_content"/>
		<p><input type="submit" value="전송"/>
	</form:form>
</body>
</html>