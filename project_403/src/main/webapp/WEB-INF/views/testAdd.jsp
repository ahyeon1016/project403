<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

<body>
<!-- 시험지 작성 공간 -->
testAdd 페이지
<p><a href="../">Home</a>
<form:form modelAttribute="NewTest" action="./testAdd">
	<fieldset>
		<div>
			작성자ID: <form:input path="mem_id" />
		</div>
		<div>
			공개 또는 비공개: <form:input path="test_time" />
		</div>
		<div>
			시험날짜: <form:input path="test_date" />
		</div>
		<div>
			시험이름: <form:input path="test_name" />
		</div>
		<div class="col-sm-offset-2 col-sm-10">
			<input type="submit" value="작성하기">
		</div>
	</fieldset>
</form:form>
</body>
</html>
