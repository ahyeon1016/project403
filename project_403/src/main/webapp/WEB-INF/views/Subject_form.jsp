<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	HELLO SUBJECT FORM
	<form:form modelAttribute="subject" method="POST" action="sub_form">
		<form:input path="sub_name"/>
	</form:form>
</body>
</html>